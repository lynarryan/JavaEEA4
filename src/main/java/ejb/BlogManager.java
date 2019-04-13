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
        BlogUser bu = blog.getBlogUser();
        bu.addBlog(blog);
        em.persist(blog);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateBlog(Blog blogWithUpdatedFields) {
        Blog stored = em.find(Blog.class,blogWithUpdatedFields.getId());
        stored.setBlogName(blogWithUpdatedFields.getBlogName());
        stored.getAudit().setUpdatedDate(blogWithUpdatedFields.getAudit().getUpdatedDate());
        em.merge(stored);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteBlog(int id) {
        em.remove(em.find(Blog.class,id));
    }

    public Blog getBlogById(int id) {
        return em.find(Blog.class, id);
    }
}
