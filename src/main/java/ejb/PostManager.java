package ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.POST;

import models.Blog;
import models.BlogPost;

@Stateless
public class PostManager extends ManagerBeans {

    @PersistenceContext(name = "assignment4")
    protected EntityManager em;

    public BlogPost findByID(int id) {
        return em.find(BlogPost.class, id);
    }

    public void add(BlogPost postToAdd) {
        System.out.println("Here");
        System.out.println(postToAdd.toString());
        em.persist(postToAdd);
    }

    public void update(BlogPost toUpdate) {
        BlogPost post = em.find(BlogPost.class, toUpdate.getId());
        post.setPostText(toUpdate.getPostText());
        post.getAudit().setUpdatedDate(post.getAudit().getUpdatedDate());
        em.merge(post);
    }

    public void remove(int id) {
        em.remove(em.find(BlogPost.class, id));
    }

    public List<BlogPost> list() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BlogPost> cq = cb.createQuery(BlogPost.class);
        cq.select(cq.from(BlogPost.class));
        return em.createQuery(cq).getResultList();
    }

}
