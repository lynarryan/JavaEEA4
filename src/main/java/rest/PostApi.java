/**********************************************************************egg*m******a******n********************
 * File: PostApi.java
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
import ejb.PostManager;
import models.Blog;
import models.BlogPost;

@Path("/post")
@RolesAllowed("USER")
public class PostApi {

    @Context
    protected SecurityContext sc;

    @EJB
    PostManager postBean;

    /**
     * Find a post by id
     * 
     * @param id
     * @return JSON response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find")
    public Response findByID(@QueryParam("id") int id) {
        BlogPost result = postBean.findByID(id);
        if (result != null) {
            return Response.ok().entity(postBean.findByID(id)).build();
        } else {
            return Response.status(404).entity("No Post Found with Provided ID").build();
        }
    }

    /**
     * Add a post
     * 
     * @param postToAdd
     * @return JSON response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPost(BlogPost postToAdd) {
        postBean.add(postToAdd);
        return Response.ok().build();
    }

    /**
     * Update post
     * 
     * @param toUpdate
     * @return JSON response
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(BlogPost toUpdate) {
        postBean.update(toUpdate);
        return Response.ok().build();
    }

    /**
     * Delete a post
     * 
     * @param id
     * @return JSON response
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@QueryParam("id") int id) {
        postBean.remove(id);
        return Response.ok().build();
    }

    /**
     * Get a list of posts
     * 
     * @return JSON response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response listPosts() {
        System.out.println("listPosts");
        List<BlogPost> result = postBean.list();
        if (!result.isEmpty()) {
            return Response.ok().entity(result).build();
        } else {
            return Response.status(404).entity("Unable to produce a list of posts").build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/blog")
    public Response listPostsByBlog(Blog body) {
        List<BlogPost> res = postBean.getPostsByBlog(body);
        if (!res.isEmpty()) {
            return Response.ok().entity(res).build();
        } else {
            return Response.status(404).entity("Unable to Produce a list of posts with provided blog").build();
        }
    }

}
