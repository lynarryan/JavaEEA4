package models;

import java.io.Serializable;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
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
    protected BlogUser user;
    protected BlogPost post;
    
    
    public String getCommentText() {
        return commentText;
    }
    
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    @JsonbTransient
    @ManyToOne
    @JoinColumn(name="OWNING_USER_ID", nullable=false)
    public BlogUser getBlogUser() {
        return user;
    }
    
    public void setBlogUser(BlogUser user) {
        this.user = user;
    }
    @JsonbTransient
    @ManyToOne
    @JoinColumn(name="POST_ID", nullable=false)
    public BlogPost getPost() {
        return post;
    }
    public void setPost(BlogPost post) {
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
