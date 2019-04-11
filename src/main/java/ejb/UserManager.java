package ejb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import models.BlogUser;
import models.PlatformRole;
import models.PlatformUser;

@Stateless
public class UserManager extends ManagerBeans {
    
    @PersistenceContext(name = "assignment4")
    protected EntityManager em;
    
    public void createUser(String firstName, String lastName, String email, String userName, String password ) {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setUserName(userName);
        platformUser.setPwHash(password);
        Set<PlatformRole> platformRole = new HashSet<>();
        platformUser.setPlatformRoles(platformRole);
        
        BlogUser blogUser = new BlogUser();
        blogUser.setFirstName(firstName);
        blogUser.setLastName(lastName);
        blogUser.setEmail(email);
        blogUser.setPlatformUser(platformUser);
        
        em.getTransaction().begin();
        em.persist(platformUser);
        em.persist(blogUser);
        em.getTransaction().commit();  
       
    }
    
    public BlogUser readUserByUsername(String userName) {
        BlogUser bu = (BlogUser)em.createQuery(
                "SELECT b FROM BlogUser b JOIN PlatformUser p WHERE p.username = :username ")
                .setParameter("username", userName)
                .getSingleResult();
        return bu;
        
    }
    
    public PlatformUser readEmail(String email) {
        PlatformUser pu = (PlatformUser)em.createQuery(
                "SELECT p FROM PlatformUser p JOIN BlogUser b WHERE b.email = :email ")
                .setParameter("email", email)
                .getSingleResult();
        return pu;
        
    }
    
    public void updateByUsername(String firstName, String lastName, String email, String userName, String password) {
        PlatformUser platformUser = em.find(PlatformUser.class, userName);
        platformUser.setUserName(userName);
        platformUser.setPwHash(password);
        Set<PlatformRole> platformRole = new HashSet<>();
        platformUser.setPlatformRoles(platformRole);
        
        BlogUser blogUser = em.find(BlogUser.class, email);
        blogUser.setFirstName(firstName);
        blogUser.setLastName(lastName);
        blogUser.setEmail(email);
        blogUser.setPlatformUser(platformUser);
        
        em.getTransaction().begin();
        em.persist(platformUser);
        em.persist(blogUser);
        em.getTransaction().commit();  
    }
    
    public void deleteByUserName(String userName) {
        PlatformUser platformUser = em.find(PlatformUser.class, userName);
        em.getTransaction().begin();
        em.remove(platformUser);
        em.getTransaction().commit();
        em.close();
    }
    
    public void deleteByEmail(String email) {
        BlogUser blogUser = em.find(BlogUser.class, email);
        em.getTransaction().begin();
        em.remove(blogUser);
        em.getTransaction().commit();
        em.close();
    }
    
    //get everybody, in rest demo
    
    public List<PlatformUser> getUsernameList() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PlatformUser> cq = cb.createQuery(PlatformUser.class);
        cq.select(cq.from(PlatformUser.class));
        return em.createQuery(cq).getResultList();
    }
   

}
