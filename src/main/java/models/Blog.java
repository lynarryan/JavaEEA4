/**********************************************************************egg*m******a******n********************
 * File: Blog.java
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

@Entity
@EntityListeners({ AuditListener.class })
@Table(name = "BLOG")
public class Blog extends ModelBase implements Serializable {

    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    protected BlogUser user;
    protected String blogName;
    private List<BlogPost> posts = new ArrayList<>();

    /**
     * No-arg constructor
     */
    public Blog() {
        super();
    }

    /**
     * Getter for posts
     * @return list of posts
     */
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    public List<BlogPost> getBlogPosts() {
        return posts;
    }

    /**
     * Setter for posts
     * @param posts
     */
    public void setBlogPosts(List<BlogPost> posts) {
        this.posts = posts;
    }

    /**
     * Add a single post
     * @param post
     */
    public void addBlog(BlogPost post) {
        if (!this.posts.contains(post)) {
            this.posts.add(post);
            post.setBlog(this);
        }
    }

    /**
     * Getter for User
     * @return The BlogUser
     */
    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "BLOG_USER_ID", nullable = true)
    public BlogUser getBlogUser() {
        return this.user;
    }

    /**
     * Setter for user
     * @param user
     */
    public void setBlogUser(BlogUser user) {
        this.user = user;
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
        if (!(obj instanceof Blog)) {
            return false;
        }
        Blog other = (Blog) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    /**
     * Getter for blogName
     * @return the name of the blog
     */
    public String getBlogName() {
        return blogName;
    }

    /**
     * Setter for blogName
     * @param blogName
     */
    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

}
