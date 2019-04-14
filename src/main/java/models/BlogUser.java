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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PLAT_USER_ID")
    public PlatformUser getPlatformUser() {
        return platformUser;
    }

    public void setPlatformUser(PlatformUser platformUser) {
        this.platformUser = platformUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public void addBlog(Blog blog) {
        if (!this.blogs.contains(blog)) {
            System.out.println("Adding Blog: " + blog.getId());
            this.blogs.add(blog);
            blog.setBlogUser(this);
        }
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


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
