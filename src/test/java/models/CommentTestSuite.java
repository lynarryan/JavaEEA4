/**********************************************************************egg*m******a******n********************
 * File: CommentTestSuite.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */

package models;

import static models.TestSuiteConstants.buildEntityManagerFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentTestSuite implements TestSuiteConstants {
    
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
     * Test that there are no comments on blog posts prior to the tests being run
     */
    @Test
    public void _01_test_no_comments_at_start() {
        EntityManager em = emf.createEntityManager();
        Comment comment = em.find(Comment.class, Integer.valueOf(1));
        assertNull(comment);
        em.close();
    }
    
    // C-R-U-D lifecycle
    /**
     * Test create a single blog post comment
     */
    @Test
    public void _02_test_create_comment_on_blog_post() {
        EntityManager em = emf.createEntityManager();
        Blog blog = new Blog();
        BlogPost blogPost = new BlogPost();
        BlogUser blogUser1 = new BlogUser();
        BlogUser blogUser2 = new BlogUser();
        Comment comment = new Comment();

        comment.setCommentText("I like pretzels too.");
        em.getTransaction().begin();
        blog.setBlogUser(blogUser1);
        blogPost.setBlog(blog);
        blogPost.addComment(comment);  
        blogUser2.addComment(comment);    
        comment.setBlogUser(blogUser2);
        comment.setPost(blogPost);
        
        em.persist(blog);
        em.persist(blogPost);
        em.persist(blogUser1);
        em.persist(blogUser2);
        em.persist(comment);

        em.getTransaction().commit();     
        BlogPost bp = em.find(BlogPost.class, Integer.valueOf(blogPost.getId()));
        assertEquals(bp, blogPost);
        em.close();
    }
    
    /**
     * Test read a comment on a blog post.
     */
    @Test
    public void _03_test_read_comment_blog_post () {
        EntityManager em = emf.createEntityManager();       
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Comment> ecq = cb.createQuery(Comment.class);
        Root<Comment> rootEmpQuery = ecq.from(Comment.class);
        ecq.select(rootEmpQuery); 
        ecq.where(cb.and(
        cb.equal(rootEmpQuery.get(Comment_.id), Integer.valueOf(1))));
        TypedQuery<Comment> eq = em.createQuery(ecq);
        Comment c = eq.getSingleResult();
        assertEquals(c.getCommentText(), "I like pretzels too.");             
        em.close(); 
    }
    
    /**
     * Test update a comment on a blog post
     */
    @Test
    public void _04_test_update_comment_blog_post() {
        EntityManager em = emf.createEntityManager();
        Comment comment = em.find(Comment.class, Integer.valueOf(1));    
        comment.setCommentText("I Like Cheese Bits in my Party Mix");
        em.getTransaction().begin();  
        em.persist(comment);
        em.getTransaction().commit();            
        Comment c = em.find(Comment.class, Integer.valueOf(comment.getId()));       
        assertEquals(c.getCommentText(), "I Like Cheese Bits in my Party Mix");       
        em.close(); 
    }
    
    /**
     * Test delete a comment on a blog post
     */
    @Test
    public void _05_test_delete_comment_blog_post() {       
        EntityManager em = emf.createEntityManager();
        Comment comment = em.find(Comment.class, Integer.valueOf(1));
        em.getTransaction().begin();
        comment.getPost().getComment().remove(comment);
        comment.getBlogUser().getComments().remove(comment);
        em.remove(comment);       
        em.getTransaction().commit();      
        Comment com = em.find(Comment.class, Integer.valueOf(comment.getId())); 
        assertNull(com);
        em.close(); 
    }
    
    /**
     * Test find the blog post with cheese in the post title
     */
    @Test
    public void _06_test_find_comment_by_text_in_blog_post_comment_text () {       
        EntityManager em = emf.createEntityManager();
      
        BlogPost blogPost = em.find(BlogPost.class, Integer.valueOf(1));
        
        BlogUser blogUser2 = em.find(BlogUser.class, Integer.valueOf(2));        
        Comment c1 = new Comment();
        Comment c2 = new Comment();
        Comment c3 = new Comment();    
         
        em.getTransaction().begin();
        c1.setCommentText("I LOVE Cheese Bits in my Party Mix");
        c2.setCommentText("I LOVE Pretzels in my Party Mix"); 
        c3.setCommentText("I LOVE Squares in my Party Mix");
            
        blogPost.addComment(c1); 
        blogPost.addComment(c2); 
        blogPost.addComment(c3); 
        blogUser2.addComment(c1);  
        blogUser2.addComment(c2);
        blogUser2.addComment(c3);
        c1.setBlogUser(blogUser2);
        c2.setBlogUser(blogUser2);
        c3.setBlogUser(blogUser2);
        c1.setPost(blogPost);   
        c2.setPost(blogPost); 
        c3.setPost(blogPost); 
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);       
        em.getTransaction().commit(); 
      
        Comment com = (Comment)em.createQuery(
                "SELECT c FROM Comment c WHERE c.commentText LIKE :ct")
                .setParameter("ct", "%Pretzels%")
                .getSingleResult();
        assertEquals(com, c2);
        em.close();  
    }
    
    /**
     * Test find all the comments on a blog post
     */
    @Test
    public void _07_test_find_all_comments_from_blog_post() {
        
        EntityManager em = emf.createEntityManager();
        BlogPost post = em.find(BlogPost.class, Integer.valueOf(1));
        List<Comment> comments = em.createQuery("SELECT c FROM Comment c WHERE c.post = :post")
                .setParameter("post", post)
                .getResultList();
        
        assertTrue(comments.size() == 3);    
        em.close();  
        
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
