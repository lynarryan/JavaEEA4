/**********************************************************************egg*m******a******n********************
 * File: A4IdentityStore.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package security;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import org.glassfish.soteria.WrappingCallerPrincipal;

import ejb.PlatformUserManager;
import ejb.UserManager;
import models.PlatformRole;
import models.PlatformUser;

@ApplicationScoped
@Default
public class A4IdentityStore implements IdentityStore {

    @Inject
    protected PlatformUserManager jpa;
    @Inject
    protected Pbkdf2PasswordHash pwHash;

    @Override
    public CredentialValidationResult validate(Credential cred) {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        if (cred instanceof UsernamePasswordCredential) {
            
            String userName = ((UsernamePasswordCredential) cred).getCaller();
            String password = ((UsernamePasswordCredential) cred).getPasswordAsString();
            PlatformUser pUser = jpa.findByName(userName);
            if(pUser!=null) {
                String pHash = pUser.getPwHash();
                try {
                    boolean verified = pwHash.verify(password.toCharArray(), pHash);
                    if(verified) {
                        result = new CredentialValidationResult(new WrappingCallerPrincipal(pUser),getRoleNamesFromPlatformRoles(pUser.getPlatformRoles()));
                                
                    }
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
            
        } else if (cred instanceof CallerOnlyCredential) {

        }
        return result;
    }

    private Set<String> getRoleNamesFromPlatformRoles(Set<PlatformRole> platformRoles) {
        Set<String> roleNames = new HashSet<String>();
        if(!platformRoles.isEmpty()) {
            roleNames = platformRoles.stream().map(s -> s.getRoleName()).collect(Collectors.toSet());
        }
        return roleNames;
    }
}
