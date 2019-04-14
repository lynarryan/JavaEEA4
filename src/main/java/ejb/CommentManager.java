/**********************************************************************egg*m******a******n********************
 * File: CommentManager.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import models.BlogPost;
import models.Comment;

@Stateless
public class CommentManager {

    @PersistenceContext(unitName = "assignment4")
    protected EntityManager em;
    /**
     * Find a post by id from the database
     * @param id
     * @return
     */
    public Comment findById(int id) {
        return em.find(Comment.class, id);
    }

    /**
     * Create a post and put it into the database
     * @param toCreate
     */
    public void createComment(Comment toCreate) {
        em.persist(toCreate);
    }

    /**
     * find the post to modify and update it accordingly in the database
     * @param toUpdate
     */
    public void updateComment(Comment toUpdate) {
        Comment comment = em.find(Comment.class, toUpdate.getId());
        comment.setCommentText(toUpdate.getCommentText());
        comment.getAudit().setUpdatedDate(toUpdate.getAudit().getUpdatedDate());
        em.merge(comment);
    }
    /**
     * Remove a specified post from the database with a provided id
     * @param id
     */
    public void deleteComment(int id) {
        em.remove(em.find(Comment.class, id));
    }
    /**
     * List all comments in the database
     * @return
     */
    public List<Comment> listComments() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        cq.select(cq.from(Comment.class));
        return em.createQuery(cq).getResultList();
    }
    /**
     * List All comments found that are attached to a specific post
     * @param post
     * @return
     */
    public List<Comment> listComments(BlogPost post) {
        List<Comment> comments = em.createQuery("SELECT c FROM Comment c WHERE c.post = :post")
                .setParameter("post", post).getResultList();
        return comments;
    }
}
