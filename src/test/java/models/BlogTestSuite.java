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

    @Test
    public void _03_test_Find_Blog_By_name() {
        EntityManager em = emf.createEntityManager();
        Blog blog = (Blog) em.createQuery("SELECT b FROM Blog b where b.blogName = :bn").setParameter("bn", "My Blog")
                .getSingleResult();

        assertEquals(blog.getBlogName(), "My Blog");
        assertTrue(blog.getId() == 1);
        assertEquals(blog.getBlogUser().getFirstName(), "Greg");
    }

    @Test
    public void _04_test_Find_Blogs_by_user() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        BlogUser user = em.find(BlogUser.class, Integer.valueOf(1));
        Blog blog1 = new Blog();
        blog1.setBlogName("Blog1");
        user.addBlog(blog1);
        blog1.setBlogUser(user);

        Blog blog2 = new Blog();
        blog2.setBlogName("Blog2");
        user.addBlog(blog2);
        blog2.setBlogUser(user);

        Blog blog3 = new Blog();
        blog3.setBlogName("Blog3");
        user.addBlog(blog3);
        blog3.setBlogUser(user);

        Blog blog4 = new Blog();
        blog4.setBlogName("Blog4");
        user.addBlog(blog4);
        blog4.setBlogUser(user);

        em.persist(blog1);
        em.persist(blog2);
        em.persist(blog3);
        em.persist(blog4);
        em.getTransaction().commit();

        List<Blog> blogs = em.createQuery("SELECT b FROM Blog b JOIN BlogUser u where u.firstName = :fn")
                .setParameter("fn", "Greg").getResultList();

        assertTrue(blogs.size() == 5);
        assertEquals(blogs.get(0).getBlogName(), "My Blog");
        assertEquals(blogs.get(1).getBlogName(), "Blog3");
        assertEquals(blogs.get(2).getBlogName(), "Blog1");
        assertEquals(blogs.get(3).getBlogName(), "Blog4");
        assertEquals(blogs.get(4).getBlogName(), "Blog2");

    }

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
