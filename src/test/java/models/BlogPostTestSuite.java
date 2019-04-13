/**********************************************************************egg*m******a******n********************
 * File: EmployeeTestSuite.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 * @author (editor/student) Lauren Preston 040-839-284
 * @author (editor/student) Gregory Leverton 040-885-599
 * 
 */

package models;



import static models.TestSuiteConstants.attachListAppender;
import static models.TestSuiteConstants.buildEntityManagerFactory;
import static models.TestSuiteConstants.detachListAppender;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogPostTestSuite implements TestSuiteConstants {

    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);
    private static final ch.qos.logback.classic.Logger eclipselinkSqlLogger =
        (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(ECLIPSELINK_LOGGING_SQL);
    
    
    
    
    // test fixture(s)
    public static EntityManagerFactory emf;
    public static Server server;

    /**
     *  Set up the test class
     */
    @BeforeClass
    public static void oneTimeSetUp() {
        try {
            logger.debug("oneTimeSetUp");
            // create in-process H2 server so we can 'see' into database
            // use "jdbc:h2:tcp://localhost:9092/mem:assignment3-testing" in Db Perspective
            // (connection in .dbeaver-data-sources.xml so should be immediately useable
            server = Server.createTcpServer().start();
            emf = buildEntityManagerFactory(_thisClaz.getSimpleName());
        }
        catch (Exception e) {
            logger.error("something went wrong building EntityManagerFactory", e);
        }
    }

    
    /**
     * Test that there are no blog posts prior to the tests being run
     */
    @Test
    public void _01_test_no_blog_post_at_start() {
        EntityManager em = emf.createEntityManager();
        BlogPost blogPost = em.find(BlogPost.class, Integer.valueOf(1));
        assertNull(blogPost);
        em.close();
    }

    // C-R-U-D lifecycle
    /**
     * Test create a single blog post
     */
    @Test
    public void _02_test_create_blog_post() {
        EntityManager em = emf.createEntityManager();
        BlogPost blogPost = new BlogPost(); 
        Blog blog = new Blog();
        BlogUser blogUser = new BlogUser();
        blogPost.setPostTitle("I Like Pretzels in my Party Mix");
        blog.setBlogName("Pretzel Lover");
        blogPost.setPostText("Pretzels are the best part of a Party Mix bag of chips. I think they're better than the cheese bits");
        blogPost.setBlog(blog);
        blog.addBlog(blogPost);
        blog.setBlogUser(blogUser);
        blogUser.addBlog(blog);
        em.getTransaction().begin();
        em.persist(blogPost);
        em.persist(blog);
        em.persist(blogUser);
        em.getTransaction().commit();     
        BlogPost bp = em.find(BlogPost.class, Integer.valueOf(blogPost.getId()));
        assertEquals(bp, blogPost);
        em.close();
    }
    
    /**
     * Test read an blog post
     */
    @Test
    public void _03_test_read_blog_post() {
        EntityManager em = emf.createEntityManager();    
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BlogPost> ecq = cb.createQuery(BlogPost.class);
        Root<BlogPost> rootEmpQuery = ecq.from(BlogPost.class);
        ecq.select(rootEmpQuery); 
        ecq.where(cb.and(
        cb.equal(rootEmpQuery.get(BlogPost_.id), Integer.valueOf(1))));
        TypedQuery<BlogPost> eq = em.createQuery(ecq);
        BlogPost bp = eq.getSingleResult();
        assertEquals(bp.getPostTitle(), "I Like Pretzels in my Party Mix");             
        em.close(); 
    }
    
    /**
     * Test update an blog post
     */
    @Test
    public void _04_test_update_blog_post() {
        EntityManager em = emf.createEntityManager();
        BlogPost blogPost = em.find(BlogPost.class, Integer.valueOf(1));    
        em.getTransaction().begin();       
        blogPost.setPostTitle("I Like Cheese Bits in my Party Mix");      
        blogPost.setPostText("Cheese bits are the best part of a Party Mix bag of chips. I think they're better than the pretzels.");
        em.getTransaction().commit();            
        BlogPost bp = em.find(BlogPost.class, Integer.valueOf(blogPost.getId()));       
        assertEquals(bp.getPostTitle(), "I Like Cheese Bits in my Party Mix");       
        assertEquals(bp.getPostText(), "Cheese bits are the best part of a Party Mix bag of chips. I think they're better than the pretzels.");     
        em.close(); 
    }
    
    /**
     * Test delete an blog post
     */
    @Test
    public void _05_test_delete_blog_post() {
        
        EntityManager em = emf.createEntityManager();
        BlogPost blogPost = em.find(BlogPost.class, Integer.valueOf(1));      
        em.getTransaction().begin();
        blogPost.getBlog().getBlogs().remove(blogPost);
        em.remove(blogPost);       
        em.getTransaction().commit();      
        BlogPost bp = em.find(BlogPost.class, Integer.valueOf(blogPost.getId())); 
        assertNull(bp);  
        em.close(); 
    }
    
    /**
     * Test find the blog post with cheese in the post title
     */
    @Test
    public void _06_test_find_blog_post_title () {        
        EntityManager em = emf.createEntityManager();
        Blog blog = em.find(Blog.class, Integer.valueOf(1));   
        BlogPost bp1 = new BlogPost();
        BlogPost bp2 = new BlogPost();
        BlogPost bp3 = new BlogPost();
         
        em.getTransaction().begin();
        bp1.setPostTitle("I Like Cheese Bits in my Party Mix"); 
        bp2.setPostTitle("I Like Pretzels in my Party Mix"); 
        bp3.setPostTitle("I Like Squares in my Party Mix"); 
        bp1.setBlog(blog);
        bp2.setBlog(blog);
        bp3.setBlog(blog);
        blog.addBlog(bp1);
        blog.addBlog(bp2);
        blog.addBlog(bp3);      
        em.persist(bp1);
        em.persist(bp2);
        em.persist(bp3);       
        em.getTransaction().commit(); 
      
        BlogPost blogP = (BlogPost)em.createQuery(
                "SELECT b FROM BlogPost b WHERE b.postTitle LIKE :pt")
                .setParameter("pt", "%Cheese%")
                .getSingleResult();
        assertEquals(blogP, bp1);
        em.close();  
    }
    
    
    /**
     * Test find all the blog posts
     */
    @Test
    public void _07_test_find_all_blog_posts_by_blog() {
        
        EntityManager em = emf.createEntityManager();
        Blog blog = em.find(Blog.class, Integer.valueOf(1));       
        List<BlogPost> bps = em.createQuery("SELECT p FROM BlogPost p WHERE p.blog = :blog")
                .setParameter("blog", blog)
                .getResultList();
        assertTrue(bps.size() == 3);       
        
    }

    /**
     * Clean up after all tests are run
     */
    @AfterClass
    public static void oneTimeTearDown() {
        logger.debug("oneTimeTearDown");
        if (emf != null) {
            emf.close();
        }
        if (server != null) {
            server.stop();
        }
    }

}

