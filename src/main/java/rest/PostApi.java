package rest;

import javax.ejb.EJB;
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

@Path("/post")
public class PostApi {
    @EJB
    protected PostManager postBean;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find")
    public Response findByID(@QueryParam("id") int id) {
        // TODO Find by ID + Behaviour
        return Response.ok().entity("" + id).build();
    }

    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(String body) {
        // Test
        System.out.println("body: " + body);
        return Response.ok().build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(String body) {
        return Response.ok().entity("ToUpdateUser: " + body).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@QueryParam("id") int id) {
        return Response.ok().entity("Deleteing user " + id).build();
    }

}
