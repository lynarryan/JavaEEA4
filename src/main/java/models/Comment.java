/**********************************************************************egg*m******a******n********************
 * File: Comment.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package models;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The Post class demonstrates:
 * <ul>
 * <li>Generated Id
 * <li>Version locking
 * <li>ManyToOne mapping
 * </ul>
 */
@Entity
@EntityListeners({AuditListener.class})
@Table(name="COMMENT")
public class Comment extends ModelBase implements Serializable {
    
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    
    protected String commentText;
    protected BlogUser user;
    protected BlogPost post;
    
    
    /**
     * Getter for commentText
     * @return the comment text
     */
    public String getCommentText() {
        return commentText;
    }
    
    /**
     * Setter for commentText
     * @param commentText
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    /**
     * Getter for user
     * @return the author of the comment
     */
    @JsonbTransient
    @ManyToOne
    @JoinColumn(name="OWNING_USER_ID", nullable=false)
    public BlogUser getBlogUser() {
        return user;
    }
    
    /**
     * Setter for user
     * @param user
     */
    public void setBlogUser(BlogUser user) {
        this.user = user;
    }
    /**
     * Getter for post
     * @return The post the comment is on
     */
    @JsonbTransient
    @ManyToOne
    @JoinColumn(name="POST_ID", nullable=false)
    public BlogPost getPost() {
        return post;
    }
    /**
     * Setter for post
     * @param post
     */
    public void setPost(BlogPost post) {
        this.post = post;
    }
    
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Comment)) {
            return false;
        }
        Comment other = (Comment)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
