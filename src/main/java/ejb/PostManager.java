package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;

import models.BlogPost;

@Stateless
public class PostManager extends ManagerBeans {
    
    @PersistenceContext(name= "assignment4")
    protected EntityManager em;
    
   

}
