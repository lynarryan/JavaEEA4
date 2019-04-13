/**********************************************************************egg*m******a******n********************
 * File: EmployeeTestSuite.java
 * Course materials (19W) CST 8277
 * @author (original) Mike Norman
 * @author (editor/student) Lauren Preston 040-839-284
 * @author (editor/student) Gregory Leverton 040-885-599
 * 
 */
package models;


import static models.TestSuiteConstants.buildEntityManagerFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogUserTestSuite implements TestSuiteConstants {

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
     * Test that there are no employees prior to the tests being run
     */
    @Test
    public void _01_test_no_BlogUsers_at_start() {
        EntityManager em = emf.createEntityManager();
        BlogUser blogUser = em.find(BlogUser.class, Integer.valueOf(1));
        PlatformUser platformUser = em.find(PlatformUser.class, Integer.valueOf(1));
        assertNull(blogUser);
        assertNull(platformUser);
        em.close();
    }

    // C-R-U-D lifecycle
    @Test
    public void _02_test_Create_BlogUser() {
        EntityManager em = emf.createEntityManager();
        
        
        BlogUser bu = new BlogUser();
        PlatformUser pu = new PlatformUser();
        bu.setFirstName("Greg");
        pu.setUserName("test");
       
        PlatformRole pr = new PlatformRole();
        pr.setRoleName("User");
        PlatformRole prA = new PlatformRole();
        pr.setRoleName("Admin");
       // PlatformRole pr = em.find(PlatformRole.class, Integer.valueOf(2));
                
        em.getTransaction().begin();
        bu.setPlatformUser(pu);
       
        pu.addPlatformRole(pr);
        pr.addPlatformUser(pu);
        em.persist(bu);
        em.persist(pu);
        em.persist(pr);
        em.persist(prA);
        em.getTransaction().commit();
        
        BlogUser blogUser = em.find(BlogUser.class, Integer.valueOf(1));
         PlatformUser platformUser = em.find(PlatformUser.class, Integer.valueOf(1));
        
        
        assertEquals(bu, blogUser);
        assertEquals(pu, platformUser);        
    }
    
    @Test
    public void _03_test_Find_Blog_User_By_name() {
        EntityManager em = emf.createEntityManager();
        BlogUser blogUser = (BlogUser)em.createQuery(
                "SELECT b FROM BlogUser b where b.firstName = :fn")
                .setParameter("fn", "Greg")
                .getSingleResult();
        
        assertEquals(blogUser.getFirstName(), "Greg");
        assertTrue(blogUser.getId() ==1);
        assertEquals(blogUser.getPlatformUser().getUserName(), "test");
    }
    
    @Test
    public void _04_test_Find_Blog_Users() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        PlatformRole role = em.find(PlatformRole.class, Integer.valueOf(2));
        BlogUser blogUser1 = new BlogUser();
        blogUser1.setFirstName("BlogUser1");
        PlatformUser platformUser1 = new PlatformUser();
        platformUser1.setUserName("PlatformUser1");
        blogUser1.setPlatformUser(platformUser1);
        platformUser1.addPlatformRole(role);
        role.addPlatformUser(platformUser1);
        
        BlogUser blogUser2 = new BlogUser();
        blogUser2.setFirstName("BlogUser2");
        PlatformUser platformUser2 = new PlatformUser();
        platformUser2.setUserName("PlatformUser2");
        blogUser2.setPlatformUser(platformUser2);
        platformUser2.addPlatformRole(role);
        role.addPlatformUser(platformUser2);

        BlogUser blogUser3 = new BlogUser();
        blogUser3.setFirstName("BlogUser3");
        PlatformUser platformUser3 = new PlatformUser();
        platformUser3.setUserName("PlatformUser3");
        blogUser3.setPlatformUser(platformUser3);
        platformUser3.addPlatformRole(role);
        role.addPlatformUser(platformUser3);

        em.persist(blogUser1);
        em.persist(blogUser2);
        em.persist(blogUser3);
        em.persist(platformUser1);
        em.persist(platformUser2);
        em.persist(platformUser3);
        em.getTransaction().commit();
        
        List<BlogUser> blogUsers = em.createQuery(
                "SELECT b FROM BlogUser b")
                 .getResultList();
              
        assertTrue(blogUsers.size() ==4);
        assertEquals(blogUsers.get(0).getFirstName(), "Greg");
        assertEquals(blogUsers.get(1).getFirstName(), "BlogUser2");
        assertEquals(blogUsers.get(2).getFirstName(), "BlogUser1");
        assertEquals(blogUsers.get(3).getFirstName(), "BlogUser3");
        assertEquals(blogUsers.get(0).getPlatformUser().getUserName(), "test");
        assertEquals(blogUsers.get(1).getPlatformUser().getUserName(), "PlatformUser2");
        assertEquals(blogUsers.get(2).getPlatformUser().getUserName(), "PlatformUser1");
        assertEquals(blogUsers.get(3).getPlatformUser().getUserName(), "PlatformUser3");
         
        }
    
    @Test
    public void _05_test_find_BlogUser_By_Username() {
        EntityManager em = emf.createEntityManager();
             
        BlogUser blogUser = (BlogUser)em.createQuery(
                "SELECT b FROM BlogUser b where b.platformUser.userName = :un")
                .setParameter("un", "test")
                .getSingleResult();
   
        
        assertEquals(blogUser.getFirstName(), "Greg");
        
        
    }
    
    @Test
    public void _06_test_Update_First_Name() {
        EntityManager em = emf.createEntityManager();
             
        BlogUser blogUser = (BlogUser)em.createQuery(
                "SELECT b FROM BlogUser b where b.firstName = :fn")
                .setParameter("fn", "Greg")
                .getSingleResult();
        
        em.getTransaction().begin();
        blogUser.setFirstName("Greg2");
        em.getTransaction().commit();
        
        BlogUser blogUpdate = (BlogUser)em.createQuery(
                "SELECT b FROM BlogUser b where b.firstName = :fn")
                .setParameter("fn", "Greg2")
                .getSingleResult();
        
        List<BlogUser> blogOld = em.createQuery(
                "SELECT b FROM BlogUser b where b.firstName = :fn")
                .setParameter("fn", "Greg")
                .getResultList();
        
        assertEquals(blogUpdate.getFirstName(), "Greg2");
        assertTrue(blogOld.size()==0);
        
        
    }
    
    @Test
    public void _07_test_delete_User() {
        EntityManager em = emf.createEntityManager();
        
        BlogUser blogUser = em.find(BlogUser.class, Integer.valueOf(1));
        PlatformRole pr = em.find(PlatformRole.class, Integer.valueOf(1));
        
        em.getTransaction().begin();
       
        pr.getPlatformUsers().remove(blogUser.getPlatformUser());
        em.remove(blogUser.getPlatformUser());
        em.remove(blogUser);
        em.getTransaction().commit();
        
      
        BlogUser buRemoved = em.find(BlogUser.class, Integer.valueOf(1));
        PlatformUser puRemoved = em.find(PlatformUser.class, Integer.valueOf(1));
        
        assertNull(buRemoved);
        assertNull(puRemoved);
        
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
