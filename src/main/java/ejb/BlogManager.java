package ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import models.Blog;
import models.BlogUser;
import models.Blog_;

@Stateless
public class BlogManager extends ManagerBeans{


    public BlogManager() {
        
    }
    
    
    @PersistenceContext(unitName = "assignment4")
    protected EntityManager em;

    public List<Blog> getBlogList(BlogUser blogUser) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Blog> cq = cb.createQuery(Blog.class);
        Root<Blog> rootBlogQuery = cq.from(Blog.class);
        cq.select(cq.from(Blog.class));
        cq.where(cb.and(
                 cb.equal(rootBlogQuery.get(Blog_.blogUser), blogUser )));
        return em.createQuery(cq).getResultList();
    }
    public List<Blog> getBlogList() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Blog> cq = cb.createQuery(Blog.class);
        cq.select(cq.from(Blog.class));
        return em.createQuery(cq).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createBlog(Blog blog) {
        em.persist(blog);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateBlog(Blog blogWithUpdatedFields) {
        em.merge(blogWithUpdatedFields);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteBlog(Blog blogToDelete) {
        em.remove(blogToDelete);
    }

    public Blog getBlogById(int id) {
        return em.find(Blog.class, id);
    }
}
