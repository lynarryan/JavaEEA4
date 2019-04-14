/**********************************************************************egg*m******a******n********************
 * File: BlogUser.java
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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BLOG_USER")
@EntityListeners({ AuditListener.class })
public class BlogUser extends ModelBase implements Serializable {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected PlatformUser platformUser;
    private List<Blog> blogs = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();
    

    /**
     * Default constructor
     */
    public BlogUser() {
        super();
    }

    /**
     * Getter for firstName
     * @return The Users first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for firstName
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for lastName
     * @return the users last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for lastName
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for email
     * @return the users email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for the PlatformUSer
     * @return the PlatformUser
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PLAT_USER_ID")
    public PlatformUser getPlatformUser() {
        return platformUser;
    }

    /**
     * Setter for platformUser
     * @param platformUser
     */
    public void setPlatformUser(PlatformUser platformUser) {
        this.platformUser = platformUser;
    }

    /**
     * Setter for email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for blogs
     * @return List of the user's blogs
     */
    @OneToMany(cascade = CascadeType.ALL)
    public List<Blog> getBlogs() {
        return blogs;
    }

    /**
     * Setter for blogs
     * @param blogs
     */
    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    /**
     * Add a single blog
     * @param blog
     */
    public void addBlog(Blog blog) {
        if (!this.blogs.contains(blog)) {
            this.blogs.add(blog);
            blog.setBlogUser(this);
        }
    }
    public void removeBlog(Blog blog) {
        if(this.blogs.contains(blog)) {
            this.blogs.remove(blog);
            blog.setBlogUser(null);
        }
    }

    /**
     * Getter for comments
     * @return a list of the users comments
     */
    @OneToMany(cascade = CascadeType.ALL)
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Setter for comments
     * @param comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    /**
     * Add a single comment
     * @param comment
     */
    public void addComment(Comment comment) {
        if (!this.comments.contains(comment)) {
            this.comments.add(comment);
            comment.setBlogUser(this);
        }
    }

    /*
     * (non-Javadoc)
     * 

     * @see java.lang.Object#hashCode()
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
        if (!(obj instanceof BlogUser)) {
            return false;
        }
        BlogUser other = (BlogUser) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
