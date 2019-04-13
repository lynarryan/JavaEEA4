package models;

import static models.TestSuiteConstants.buildEntityManagerFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogPostTestSuite implements TestSuiteConstants {

    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);
    private static final ch.qos.logback.classic.Logger eclipselinkSqlLogger = (ch.qos.logback.classic.Logger) LoggerFactory
            .getLogger(ECLIPSELINK_LOGGING_SQL);

    // test fixture(s)
    public static EntityManagerFactory emf;
    public static Server server;

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
     * Test that there are no blogPosts prior to the tests being run
     */
    @Test
    public void _01_test_no_Blogs_at_start() {
        logger.debug("TEST_EMPTY");
        EntityManager em = emf.createEntityManager();
        BlogPost post = em.find(BlogPost.class, Integer.valueOf(1));
        assertNull(post);
        em.close();
    }

    // C-R-U-D lifecycle
    @Test
    public void _02_test_Create_BlogPost() {

        logger.debug("TEST_CREATE");
        EntityManager em = emf.createEntityManager();
        BlogUser user = new BlogUser();
        Blog blog = new Blog();
        BlogPost post = new BlogPost();
        user.addBlog(blog);
        blog.addBlog(post);

        em.getTransaction().begin();
        em.persist(post);
        em.persist(user);
        em.persist(blog);
        em.getTransaction().commit();

        BlogPost res = em.find(BlogPost.class, 1);
        assertEquals(res, post);

        em.getTransaction().begin();
        em.remove(post);
        em.close();
    }

    @Test
    public void _03_test_Find_BlogPost_by_user() {
        logger.debug("TEST_FIND_BY_USER");
        EntityManager em = emf.createEntityManager();
        BlogUser user = new BlogUser();
        user.setFirstName("test");
        user.setLastName("user");
        BlogPost post = new BlogPost();
        Blog blog = new Blog();
        Blog blog2 = new Blog();
        user.addBlog(blog);
        user.addBlog(blog2);
        blog.addBlog(post);

        em.getTransaction().begin();
        em.persist(user);
        em.persist(blog);
        em.persist(blog2);
        em.persist(post);
        em.getTransaction().commit();

        String findPostByUserName = "SELECT p FROM BlogPost p where p.blog IN (SELECT b FROM Blog b WHERE b.blogUser IN (SELECT bu from BlogUser bu where bu.firstName = :fn AND bu.lastName = :ln))";
        BlogPost result = em.createQuery(findPostByUserName, BlogPost.class).setParameter("fn", user.firstName)
                .setParameter("ln", user.lastName).getSingleResult();
        assertEquals(result, post);

        em.getTransaction().begin();
        em.remove(post);
        em.getTransaction().commit();
        em.close();

    }

    @Test
    public void _04_test_Find_BlogPost_by_id() {
        logger.debug("TEST_FIND_BY_ID");
        EntityManager em = emf.createEntityManager();
        BlogUser user = new BlogUser();
        user.setFirstName("test");
        user.setLastName("user");
        BlogPost post = new BlogPost();
        Blog blog = new Blog();
        user.addBlog(blog);
        blog.addBlog(post);

        em.getTransaction().begin();
        em.persist(user);
        em.persist(blog);
        em.persist(post);
        em.getTransaction().commit();

        em.refresh(post);
        BlogPost result = em.find(BlogPost.class, post.getId());

        assertEquals(result, post);

        em.getTransaction().begin();
        em.remove(post);
        em.getTransaction().commit();
        em.close();

    }

    @Test
    public void _05_test_update_BlogPost() {
        logger.debug("TEST_UPDATE");
        EntityManager em = emf.createEntityManager();
        BlogUser user = new BlogUser();
        user.setFirstName("test");
        user.setLastName("user");
        BlogPost post = new BlogPost();
        Blog blog = new Blog();
        user.addBlog(blog);
        blog.addBlog(post);
        String testVal = "Hello World";
        String testVal2 = "";
        em.getTransaction().begin();
        em.persist(user);
        em.persist(blog);
        em.persist(post);
        em.getTransaction().commit();
        em.refresh(post);

        assertEquals(post.getPostText(), em.find(BlogPost.class, post.getId()).getPostText());
        testVal2 = post.getPostText();
        post.setPostText(testVal);
        em.getTransaction().begin();
        em.merge(post);
        em.getTransaction().commit();
        BlogPost res = em.find(BlogPost.class, post.getId());
        assertEquals(testVal, res.getPostText());
        assertNotEquals(testVal2, res.getPostText());

        em.getTransaction().begin();
        em.remove(post);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void _06_test_delete_BlogPost() {
        logger.debug("TEST_REMOVE");
        EntityManager em = emf.createEntityManager();
        BlogUser user = new BlogUser();
        user.setFirstName("test");
        user.setLastName("user");
        BlogPost post = new BlogPost();
        Blog blog = new Blog();
        user.addBlog(blog);
        blog.addBlog(post);

        em.getTransaction().begin();
        em.persist(user);
        em.persist(blog);
        em.persist(post);
        em.getTransaction().commit();
        em.refresh(post);
        assertNotNull(em.find(BlogPost.class, post.getId()));
        em.getTransaction().begin();
        em.remove(post);
        em.getTransaction().commit();

        assertNull(em.find(BlogPost.class, post.getId()));
        em.close();
    }

    @Test
    public void _07_test_list_BlogPosts_by_a_user() {
        EntityManager em = emf.createEntityManager();
        BlogUser user = new BlogUser();
        user.setFirstName("test");
        user.setLastName("user");
        BlogPost post = new BlogPost();
        BlogPost post1 = new BlogPost();
        BlogPost post2 = new BlogPost();
        BlogPost post3 = new BlogPost();
        Blog blog = new Blog();
        user.addBlog(blog);
        blog.addBlog(post);
        blog.addBlog(post1);
        blog.addBlog(post2);
        blog.addBlog(post3);

        em.getTransaction().begin();
        em.persist(user);
        em.persist(blog);
        em.persist(post);
        em.getTransaction().commit();
        
        
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
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