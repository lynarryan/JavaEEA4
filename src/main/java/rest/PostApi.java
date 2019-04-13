package rest;

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
public class PostApi {
    @EJB
    PostManager postBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find")
    public Response findByID(@QueryParam("id") int id) {
        return Response.ok().entity(postBean.findByID(id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPost(BlogPost postToAdd) {
        return Response.ok().build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(BlogPost toUpdate) {
        return Response.ok().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@QueryParam("id") int id) {
        return Response.ok().build();
    }

    @GET
    @Path("/list")
    public Response listPosts() {
        return Response.ok().entity(postBean.list()).build();
    }

}
