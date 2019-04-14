/**********************************************************************egg*m******a******n********************
 * File: BlogPost.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
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
@EntityListeners({ AuditListener.class })
@Table(name = "BLOG_POST")
public class BlogPost extends ModelBase implements Serializable {

    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    protected String postTitle;
    protected String postText;
    protected Blog blog;
    protected List<Comment> comments = new ArrayList<>();

    /**
     * Getter for postTitle
     * @return the Post title
     */
    public String getPostTitle() {
        return postTitle;
    }

    /**
     * Setter for postTitle
     * @param postTitle
     */
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    /**
     * Getter for postText
     * @return The text of the post
     */
    public String getPostText() {
        return postText;
    }

    /**
     * Setter for the postText
     * @param postText
     */
    public void setPostText(String postText) {
        this.postText = postText;
    }

    /**
     *  No-arg constructor
     */
    public BlogPost() {
        super();
    }

    /**
     * Getter for comments
     * @return List of comments
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    public List<Comment> getComment() {
        return comments;
    }

    /**
     * Setter for comments
     * @param comments
     */
    public void setComment(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Add a single comment
     * @param comment
     */
    public void addComment(Comment comment) {
        comment.setPost(this);
        if (this.comments.contains(comment)) {
            this.comments.add(comment);
        }
    }

    /**
     * Setter for blog
     * @param blog
     */
    public void setBlogUser(Blog blog) {
        this.blog = blog;
    }

    /**
     * Getter for blog
     * @return the Blog connected to the post
     */
    @JsonbTransient    
    @ManyToOne
    @JoinColumn(name = "BLOG_ID", nullable = false)
    public Blog getBlog() {
        return this.blog;
    }

    /**
     * Setter for blog
     * @param blog
     */
    public void setBlog(Blog blog) {
        this.blog = blog;
        blog.addBlog(this);

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
        if (!(obj instanceof BlogPost)) {
            return false;
        }
        BlogPost other = (BlogPost) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
