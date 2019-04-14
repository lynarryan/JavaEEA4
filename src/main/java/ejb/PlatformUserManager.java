package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import models.PlatformUser;

@Stateless
public class PlatformUserManager {
    @PersistenceContext(unitName = "assignment4")
    protected EntityManager em;

    public PlatformUser findByName(String userName) {
        PlatformUser result = null;
        String findByUserName = "SELECT pu FROM PlatformUser pu WHERE username = :un";
        result = em.createQuery(findByUserName, PlatformUser.class).setParameter("un", userName).getSingleResult(); 
        return result;
    }

    public void createUser(PlatformUser pU) {
        em.persist(pU);        
    }

}
