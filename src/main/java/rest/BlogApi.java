/**********************************************************************egg*m******a******n********************
 * File: BlogApi.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ejb.BlogManager;
import models.Blog;
import models.BlogUser;

@Path("/blog")
@RolesAllowed("ADMIN")
public class BlogApi {


    @EJB
    BlogManager blogBean;
    
    @Context
    protected SecurityContext sc;

    /**
     * @param id
     * @return
     */
    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByID(@QueryParam("id") int id) {
        Blog result = blogBean.getBlogById(id);
        if (result != null) {
            return Response.ok().entity(result).build();
        } else {
            return Response.status(404).entity("Unable to find a blog with the provided id").build();
        }
    }
   

    /**
     * Create a blog
     * @param body
     * @return JSON response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBlog(Blog body) {
        blogBean.createBlog(body);
        return Response.ok().build();
    }

    /**
     * Update a blog
     * @param body
     * @return JSON response
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBlog(Blog body) {
        blogBean.updateBlog(body);
        return Response.ok().build();
    }

    /**
     * Delete a blog
     * @param id
     * @return JSON response
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response deleteBlog(@QueryParam("id") int id) {
        blogBean.deleteBlog(id);
        return Response.ok().build();
    }

    /**
     * Get blog list
     * @return JSON response
     */
    @GET
    @Path("/list")
    public Response listBlogs() {
        List<Blog> blogs = blogBean.getBlogList();
        if (!blogs.isEmpty()) {
            return Response.ok().entity(blogBean.getBlogList()).build();
        } else {
            return Response.status(404).entity("Unable to Construct a list of blogs").build();
        }

    }

    @POST
    @Path("/list/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findBlogs(BlogUser body) {
        List<Blog> result = blogBean.getBlogList(body);
        if(!result.isEmpty()) {
            return Response.ok().entity(result).build();
        }else {
            return Response.status(404).entity("Unable to Construct a list of blogs for that user").build();
        }
    }
}
