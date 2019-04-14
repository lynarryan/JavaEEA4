/**********************************************************************egg*m******a******n********************
 * File: UserManager.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import models.BlogPost;
import models.BlogUser;
import models.PlatformUser;

@Stateless
public class UserManager extends ManagerBeans {

    @Inject
    protected Pbkdf2PasswordHash pbHash;


    @PersistenceContext(name = "assignment4")
    protected EntityManager em;

    /**
     * Add a BlogUser
     * @param body
     */
    public void create(BlogUser body) {
        Map<String, String> pbProp = new HashMap<>();
        pbProp.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256");
        pbProp.put("Pbkdf2PasswordHash.Iterations", "2048");
        pbProp.put("Pbkdf2PasswordHash.SaltSizeBytes", "32");
        pbProp.put("Pbkdf2PasswordHash.KeySizeBytes", "32");
        pbHash.initialize(pbProp);
        body.getPlatformUser().setPwHash(pbHash.generate(body.getPlatformUser().getPwHash().toCharArray()));
        
        em.persist(body);
    }

    /** 
     * Find the blog user by their id
     * @param id
     * @return a Blog USer
     */
    public BlogUser find(int id) {
        return em.find(BlogUser.class, id);
    }

    /**
     * Find BlogUser by full name
     * @param firstName
     * @param lastName
     * @return The BlogUser
     */
    public BlogUser find(String firstName, String lastName) {
        BlogUser result = null;
        System.out.println("Here");
        String findByNames = "SELECT bu FROM BlogUser bu WHERE bu.firstName = :fn  and   bu.lastName =  :ln";
        try {
            result = em.createQuery(findByNames, BlogUser.class).setParameter("fn", firstName)
                    .setParameter("ln", lastName).getSingleResult();
            System.out.println(result.getFirstName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Update a BlogUser
     * @param toUpdate
     */
    public void update(BlogUser toUpdate) {
        BlogUser stored = em.find(BlogUser.class, toUpdate.getId());
        stored.setFirstName(toUpdate.getFirstName());
        stored.setLastName(toUpdate.getLastName());
        stored.setEmail(toUpdate.getEmail());
        stored.getAudit().setUpdatedDate(toUpdate.getAudit().getUpdatedDate());
        em.merge(stored);

    }

    /**
     * Delete a blog user
     * @param id
     */
    public void delete(int id) {
        em.remove(em.find(BlogUser.class, id));
    }

    /**
     * Find all users
     * @return List of all users
     */
    public List<BlogUser> list() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BlogUser> cq = cb.createQuery(BlogUser.class);
        cq.select(cq.from(BlogUser.class));
        return em.createQuery(cq).getResultList();
    }
}
