package rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/blog/")
public class BlogApi {
    
    @GET
    @Path("/find")
    public Response findByID(@QueryParam("id") int id) {
        // TODO Find by ID + Behaviour
        return Response.ok().entity("" + id).build();
    }

    
    @POST
    public Response createBlog(String body) {
        // Test
        System.out.println("body: " + body);
        return Response.ok().build();
    }

    @PUT
    public Response updateBlog(String body) {
        return Response.ok().entity("ToUpdateUser: " + body).build();
    }

    @DELETE
    public Response deleteBlog(@QueryParam("id") int id) {
        return Response.ok().entity("Deleteing user " + id).build();
    }

}
