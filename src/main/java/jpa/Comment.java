package jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    protected User user;
    protected Post post;
    
    
    public String getCommentText() {
        return commentText;
    }
    
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    
    @ManyToOne
    @JoinColumn(name="OWNING_USER_ID", nullable=false)
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @ManyToOne
    @JoinColumn(name="POST_ID", nullable=false)
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Post)) {
            return false;
        }
        Post other = (Post)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
