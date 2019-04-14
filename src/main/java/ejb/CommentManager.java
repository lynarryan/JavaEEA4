package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import models.Comment;

@Stateless
public class CommentManager {

    @PersistenceContext(unitName = "assignment4")
    protected EntityManager em;

    
    
    public Comment findById(int id) {
        return em.find(Comment.class, id);
        
    }
}
