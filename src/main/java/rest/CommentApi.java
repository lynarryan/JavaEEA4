/**********************************************************************egg*m******a******n********************
 * File: CommentApi.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package rest;

import java.util.List;

import javax.annotation.security.PermitAll;
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

import ejb.CommentManager;
import models.BlogPost;
import models.Comment;

@Path("/comment")
@RolesAllowed("USER")
public class CommentApi {
    @EJB
    CommentManager commentBean;

    @Context
    SecurityContext sc;
    /**
     * Find a Comment by a provided id
     * @param id
     * @return
     */
    @Path("/find")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByID(@QueryParam("id") int id) {
        Comment com = commentBean.findById(id);
        if (com != null) {
            return Response.ok().entity(com).build();
        } else {
            return Response.status(404).entity("Unable to find comment with provided id").build();
        }

    }
    /**
     * Create a Post from the provided JSON
     * @param body
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createComment(Comment body) {
        commentBean.createComment(body);
        return Response.ok().build();
    }
    /**
     * update a Post from the provided JSON Body
     * @param body
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateComment(Comment body) {
        commentBean.updateComment(body);
        return Response.ok().build();
    }
    /**
     * Delete a Post with the provided ID
     * @param id
     * @return
     */
    @DELETE
    @PermitAll
    public Response deleteComment(@QueryParam("id") int id) {
        commentBean.deleteComment(id);
        return Response.ok().build();
    }

    /**
     * List all comments
     * @return
     */
    @GET
    @PermitAll
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listComments() {
        List<Comment> res = commentBean.listComments();
        if (!res.isEmpty()) {
            return Response.ok().entity(res).build();
        } else {
            return Response.status(404).entity("unable to construct a list of comments").build();
        }
    }

    /**
     * List all comments at a specific post
     * @param post
     * @return
     */
    @POST
    @PermitAll
    @Path("/list/post")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCommentsByPost(BlogPost post) {
        List<Comment> res = commentBean.listComments(post);
        if (!res.isEmpty()) {
            return Response.ok().entity(res).build();
        } else {
            return Response.status(404).entity("unable to construct a list of comments").build();
        }
    }
}
