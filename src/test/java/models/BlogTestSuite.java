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

    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);
    private static final ch.qos.logback.classic.Logger eclipselinkSqlLogger =
        (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(ECLIPSELINK_LOGGING_SQL);
    
    private static final String SELECT_EMPLOYEE_1 =
            "SELECT ID, FIRSTNAME, LASTNAME, SALARY, VERSION, ADDR_ID FROM EMPLOYEE WHERE (ID = ?)";
    private static final String INSERT_EMPLOYEE_2 =
            "INSERT INTO EMPLOYEE (FIRSTNAME, LASTNAME, SALARY, VERSION, ADDR_ID) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_EMPLOYEE =
            "UPDATE EMPLOYEE SET FIRSTNAME = ?, LASTNAME = ?, VERSION = ? WHERE ((ID = ?) AND (VERSION = ?))";
    private static final String DELETE_EMPLOYEE_PROJECT_1=
            "DELETE FROM EMP_PROJ WHERE (EMP_ID = ?)";
    private static final String DELETE_EMPLOYEE=
            "DELETE FROM EMPLOYEE WHERE ((ID = ?) AND (VERSION = ?))";
    private static final String SELECT_MAX_SALARY=
            "SELECT t0.ID, t0.FIRSTNAME, t0.LASTNAME, t0.SALARY, t0.VERSION, t0.ADDR_ID FROM EMPLOYEE t0 WHERE (t0.SALARY = (SELECT MAX(t1.SALARY) FROM EMPLOYEE t1))";

    private static final String SELECT_EMPLOYEE_ALL=
            "SELECT ID, FIRSTNAME, LASTNAME, SALARY, VERSION, ADDR_ID FROM EMPLOYEE";
    private static final String UPDATE_SALARY=
            "UPDATE EMPLOYEE SET SALARY = ?, VERSION = ? WHERE ((ID = ?) AND (VERSION = ?))";
    private static final String SELECT_SALARY_GREATER=
            "SELECT ID, FIRSTNAME, LASTNAME, SALARY, VERSION, ADDR_ID FROM EMPLOYEE WHERE (SALARY >= ?)";
    private static final String SELECT_EMPLOYEE_FIRSTNAME=
            "SELECT ID, FIRSTNAME, LASTNAME, SALARY, VERSION, ADDR_ID FROM EMPLOYEE WHERE (FIRSTNAME = ?)";
    private static final String SELECT_EMPLOYEE_FULL_NAME=
            "SELECT ID, FIRSTNAME, LASTNAME, SALARY, VERSION, ADDR_ID FROM EMPLOYEE WHERE ((FIRSTNAME = ?) AND (LASTNAME = ?))";
    
    
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
    public void _01_test_no_Employees_at_start() {
        EntityManager em = emf.createEntityManager();
        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);
        Blog blog = em.find(Blog.class, Integer.valueOf(1));
        detachListAppender(eclipselinkSqlLogger, listAppender);
        assertNull(blog);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(1, loggingEvents.size());
       
       
        em.close();
    }

    // C-R-U-D lifecycle
    
    
   
   
    

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
