package models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name="BLOG_POST")
public class BlogPost extends ModelBase implements Serializable {
    
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    
    protected String postText;
    protected Blog blog;
    protected List<Comment> comment;
    
    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public BlogPost() {
        super();
    }
    
    @OneToMany(mappedBy = "post", cascade=CascadeType.REMOVE)
    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
    
    public void setBlogUser(Blog blog) {
        this.blog = blog;
    }
    
    @ManyToOne
    @JoinColumn(name="BLOG_ID", nullable=false)
    public Blog getBlog() {
        return this.blog;
    }
    public void setBlog(Blog b) {
        this.blog = b;
        b.addBlog(this);
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
        if (!(obj instanceof BlogPost)) {
            return false;
        }
        BlogPost other = (BlogPost)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
