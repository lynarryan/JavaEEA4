/**********************************************************************egg*m******a******n********************
 * File: PostManager.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Blog;
import models.BlogPost;
import models.BlogPost_;


@Stateless
public class PostManager extends ManagerBeans {

    @PersistenceContext(name = "assignment4")
    protected EntityManager em;

    /**
     * Find blog post by its id
     * @param id
     * @return Post
     */
    public BlogPost findByID(int id) {
        return em.find(BlogPost.class, id);
    }

    /**
     * Add a blog post
     * @param postToAdd
     */
    public void add(BlogPost postToAdd) {
        System.out.println("Here");
        System.out.println(postToAdd.toString());
        em.persist(postToAdd);
    }

    /**
     * Update a blog post
     * @param toUpdate
     */
    public void update(BlogPost toUpdate) {
        BlogPost post = em.find(BlogPost.class, toUpdate.getId());
        post.setPostText(toUpdate.getPostText());
        post.getAudit().setUpdatedDate(post.getAudit().getUpdatedDate());
        em.merge(post);
    }

    /**
     * Remove a blog post
     * @param id
     */
    public void remove(int id) {
        em.remove(em.find(BlogPost.class, id));
    }

    /**
     * Get a list of blog posts
     * @return List of blog posts
     */
    public List<BlogPost> list() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BlogPost> cq = cb.createQuery(BlogPost.class);
        cq.select(cq.from(BlogPost.class));
        return em.createQuery(cq).getResultList();
    }
    
    public List<BlogPost> getPostsByBlog(Blog blog){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BlogPost> cq = cb.createQuery(BlogPost.class);
        Root<BlogPost> rootBlogPostQuery = cq.from(BlogPost.class);
        cq.select(cq.from(BlogPost.class));
        cq.where(cb.and(
                 cb.equal(rootBlogPostQuery.get(BlogPost_.blog), blog )));
        return em.createQuery(cq).getResultList();
    }

}
