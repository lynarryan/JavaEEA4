package rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public class PostApi {
    @GET
    @Path("/find")
    public Response findByID(@QueryParam("id") int id) {
        // TODO Find by ID + Behaviour
        return Response.ok().entity("" + id).build();
    }

    
    @POST
    public Response createPost(String body) {
        // Test
        System.out.println("body: " + body);
        return Response.ok().build();
    }

    @PUT
    public Response updatePost(String body) {
        return Response.ok().entity("ToUpdateUser: " + body).build();
    }

    @DELETE
    public Response deletePost(@QueryParam("id") int id) {
        return Response.ok().entity("Deleteing user " + id).build();
    }

}
