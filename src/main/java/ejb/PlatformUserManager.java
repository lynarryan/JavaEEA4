/**********************************************************************egg*m******a******n********************
 * File: PlatformUserManager.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package ejb;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import models.PlatformUser;

@ApplicationScoped
@Default
public class PlatformUserManager {
    @PersistenceContext(unitName = "assignment4")
    protected EntityManager em;

    /**
     * Find PlatformUser by userName
     * @param userName
     * @return
     */
    public PlatformUser findByName(String userName) {
        PlatformUser result = null;
        String findByUserName = "SELECT pu FROM PlatformUser pu WHERE pu.userName = :un";
       try {
        result = em.createQuery(findByUserName, PlatformUser.class).setParameter("un", userName).getSingleResult();
       }catch(Exception e) {
           return null;
       }
        return result;
    }

    /**
     * Create a PlatformUser
     * @param pU
     */
    public void createUser(PlatformUser pU) {
        em.persist(pU);
    }


}
