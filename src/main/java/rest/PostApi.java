package rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ejb.PostManager;
import models.BlogPost;

@Path("/post")
@RolesAllowed("USER")
public class PostApi {
    @EJB
    PostManager postBean;

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPost(BlogPost postToAdd) {
        postBean.add(postToAdd);
        return Response.ok().build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(BlogPost toUpdate) {
        postBean.update(toUpdate);
        return Response.ok().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@QueryParam("id") int id) {
        postBean.remove(id);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response listPosts() {
        System.out.println("listPosts");
        List<BlogPost> result = postBean.list();
        if (!result.isEmpty()) {
            return Response.ok().entity(postBean.list()).build();
        } else {
            return Response.status(404).entity("Unable to produce a list of posts").build();
        }
    }

}
