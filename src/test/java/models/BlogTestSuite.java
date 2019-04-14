/**********************************************************************egg*m******a******n********************
 * File: BlogTestSuite.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
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
public class BlogTestSuite implements TestSuiteConstants {

    // test fixture(s)
    public static EntityManagerFactory emf;
    public static Server server;
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);
    private static final ch.qos.logback.classic.Logger eclipselinkSqlLogger = (ch.qos.logback.classic.Logger) LoggerFactory
            .getLogger(ECLIPSELINK_LOGGING_SQL);

    /**
     * Set up the test class
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
        } catch (Exception e) {
            logger.error("something went wrong building EntityManagerFactory", e);
        }
    }

    /**
     * Test that there are no employees prior to the tests being run
     */
    @Test
    public void _01_test_no_Blogs_at_start() {
        EntityManager em = emf.createEntityManager();
        Blog blog = em.find(Blog.class, Integer.valueOf(1));
        assertNull(blog);
        em.close();
    }

    // C-R-U-D lifecycle
    /**
     * Test create a blog
     */
    @Test
    public void _02_test_Create_Blog() {
        EntityManager em = emf.createEntityManager();

        Blog blog = new Blog();
        BlogUser bu = new BlogUser();
        blog.setBlogName("My Blog");
        bu.setFirstName("Greg");
        bu.addBlog(blog);
        blog.setBlogUser(bu);
        
        em.getTransaction().begin();
        em.persist(bu);
        em.persist(blog);
        em.getTransaction().commit();

        Blog blog2 = em.find(Blog.class, Integer.valueOf(1));

        assertEquals(blog, blog2);
        assertEquals(blog.getBlogUser(), bu);
    }

    /**
     * Test find blog by name
     */
    @Test
    public void _03_test_Find_Blog_By_name() {
        EntityManager em = emf.createEntityManager();
        Blog blog = (Blog) em.createQuery("SELECT b FROM Blog b where b.blogName = :bn").setParameter("bn", "My Blog")
                .getSingleResult();

        assertEquals(blog.getBlogName(), "My Blog");
        assertTrue(blog.getId() == 1);
        assertEquals(blog.getBlogUser().getFirstName(), "Greg");
    }

    /**
     * test find blogs by user
     */
    @Test
    public void _04_test_Find_Blogs_by_user() {
        EntityManager em = emf.createEntityManager();
        BlogUser user = new BlogUser();
        Blog blog1 = new Blog();
        Blog blog2 = new Blog();
        Blog blog3 = new Blog();
        user.setFirstName("test");
        user.setLastName("user");
        
        blog1.setBlogName("blog1");
        blog2.setBlogName("blog2");
        blog3.setBlogName("blog3");
        

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        user.addBlog(blog1);
        em.persist(blog1);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        user.addBlog(blog2);
        em.persist(blog2);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        user.addBlog(blog3);
        em.persist(blog3);
        em.getTransaction().commit();

        String findBlogsByUserName = "SELECT b FROM Blog b WHERE b.blogUser IN (SELECT bu from BlogUser bu where bu.firstName = :fn AND bu.lastName = :ln)";
        List<Blog> result = em.createQuery(findBlogsByUserName, Blog.class).setParameter("fn", user.firstName)
                .setParameter("ln", user.lastName).getResultList();

        assertTrue(result.contains(blog1));
        assertTrue(result.contains(blog2));
        assertTrue(result.contains(blog3));
        
        em.getTransaction().begin();
        em.remove(blog3);
        em.remove(blog2);
        em.remove(blog1);
        em.remove(user);
        em.getTransaction().commit();

    }

    /**
     * Test update blog
     */
    @Test
    public void _05_test_update_Blog_Title() {
        EntityManager em = emf.createEntityManager();
        Blog blog = em.find(Blog.class, Integer.valueOf(1));
        em.getTransaction().begin();
        blog.setBlogName("New Name");
        em.getTransaction().commit();

        Blog blogNew = (Blog) em.createQuery("SELECT b FROM Blog b where b.blogName = :bn")
                .setParameter("bn", "New Name").getSingleResult();

        List<Blog> blogOld = em.createQuery("SELECT b FROM Blog b where b.blogName = :bn").setParameter("bn", "My Blog")
                .getResultList();

        assertNotNull(blogNew);
        assertTrue(blogOld.size() == 0);

    }

    /**
     * test delete blog
     */
    @Test
    public void _06_test_delete_Blog() {
        EntityManager em = emf.createEntityManager();

        Blog blog = em.find(Blog.class, Integer.valueOf(1));
        BlogUser blogUser = em.find(BlogUser.class, Integer.valueOf(1));
        em.getTransaction().begin();
        em.remove(blog);
        blogUser.getBlogs().remove(0);
        em.getTransaction().commit();

        Blog blogRemoved = em.find(Blog.class, Integer.valueOf(1));
        BlogUser bu = em.find(BlogUser.class, Integer.valueOf(1));

        assertNull(blogRemoved);
        assertNotNull(bu);

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
