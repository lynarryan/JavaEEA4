/**********************************************************************egg*m******a******n********************
 * File: BuildDefaultAdmin.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package ejb;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import models.PlatformRole;
import models.PlatformUser;

@Startup
@Singleton
public class BuildDefaultAdmin {

    @Inject
    PlatformUserManager jpa;

    @Inject
    @ConfigProperty(name = "default-admin-username", defaultValue = "admin")
    private String defaultAdminUsername;

    @Inject
    @ConfigProperty(name = "default-admin-password")
    private String defaultAdminPassword;

    @Inject
    protected Pbkdf2PasswordHash pbHash;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @PostConstruct
    public void init() {
        System.out.println(defaultAdminUsername + " " + defaultAdminPassword);
        PlatformUser admin;

        admin = jpa.findByName(defaultAdminUsername);
        System.out.println("here2");
        admin = new PlatformUser();
        Map<String, String> pbProp = new HashMap<>();
        pbProp.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256");
        pbProp.put("Pbkdf2PasswordHash.Iterations", "2048");
        pbProp.put("Pbkdf2PasswordHash.SaltSizeBytes", "32");
        pbProp.put("Pbkdf2PasswordHash.KeySizeBytes", "32");
        pbHash.initialize(pbProp);

        String pwHash = pbHash.generate(defaultAdminPassword.toCharArray());
        admin.setPwHash(pwHash);
        admin.setUserName(defaultAdminUsername);
        PlatformRole platRole = new PlatformRole();
        platRole.setRoleName("ADMIN");
        Set<PlatformRole> roles = admin.getPlatformRoles();
        roles.add(platRole);
        jpa.createUser(admin);
    }

}
