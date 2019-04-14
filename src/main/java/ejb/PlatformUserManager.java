package ejb;

import javax.ejb.Stateless;
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

    public void createUser(PlatformUser pU) {
        em.persist(pU);
    }


}
