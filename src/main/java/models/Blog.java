package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public Blog() {
        super();
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    @OneToMany(mappedBy = "blog", cascade = CascadeType.REMOVE)
    public List<BlogPost> getBlogs() {
        return posts;
    }

    public void setBlogs(List<BlogPost> posts) {
        this.posts = posts;
    }

    public void addBlog(BlogPost post) {
        if (!this.posts.contains(post)) {
            this.posts.add(post);
            post.setBlog(this);
        }
    }

    public void setBlogUser(BlogUser user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "BLOG_USER_ID", nullable = false)
    public BlogUser getBlogUser() {
        return this.user;
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
        if (!(obj instanceof Blog)) {
            return false;
        }
        Blog other = (Blog) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
