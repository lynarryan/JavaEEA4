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

    public Comment findById(int id) {
        return em.find(Comment.class, id);
    }

    public void createComment(Comment toCreate) {
        em.persist(toCreate);
    }

    public void updateComment(Comment toUpdate) {
        Comment comment = em.find(Comment.class, toUpdate.getId());
        comment.setCommentText(toUpdate.getCommentText());
        comment.getAudit().setUpdatedDate(toUpdate.getAudit().getUpdatedDate());
        em.merge(comment);
    }

    public void deleteComment(int id) {
        em.remove(em.find(Comment.class, id));
    }

    public List<Comment> listComments() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        cq.select(cq.from(Comment.class));
        return em.createQuery(cq).getResultList();
    }

    public List<Comment> listComments(BlogPost post) {
        List<Comment> comments = em.createQuery("SELECT c FROM Comment c WHERE c.post = :post")
                .setParameter("post", post).getResultList();
        return comments;
    }
}
