package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import models.BlogPost;
import models.BlogUser;

@Stateless
public class UserManager extends ManagerBeans {

    @PersistenceContext(name = "assignment4")
    protected EntityManager em;

    public void create(BlogUser body) {
        em.persist(body);
    }

    public BlogUser find(int id) {
        return em.find(BlogUser.class, id);
    }

    public BlogUser find(String firstName, String lastName) {
        BlogUser result = null;
        System.out.println("Here");
        String findByNames = "SELECT bu FROM BlogUser bu WHERE bu.firstName = :fn  and   bu.lastName =  :ln";
        try {
        result = em.createQuery(findByNames, BlogUser.class)
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .getSingleResult();
        System.out.println(result.getFirstName());
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return result;
    }

    public void update(BlogUser toUpdate) {
        BlogUser stored = em.find(BlogUser.class, toUpdate.getId());
        stored.setFirstName(toUpdate.getFirstName());
        stored.setLastName(toUpdate.getLastName());
        stored.setEmail(toUpdate.getEmail());
        stored.getAudit().setUpdatedDate(toUpdate.getAudit().getUpdatedDate());
        em.merge(stored);
        
    }

    public void delete(int id) {
        em.remove(em.find(BlogUser.class, id));
    }

    public List<BlogUser> list() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BlogUser> cq = cb.createQuery(BlogUser.class);
        cq.select(cq.from(BlogUser.class));
        return em.createQuery(cq).getResultList();
    }
}
