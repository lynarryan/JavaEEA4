package rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserApi {

    @GET

    @Path("/find/id")
    public Response findByID(@QueryParam("id") int id) {
        // TODO Find by ID + Behaviour
        return Response.ok().entity("" + id).build();
    }

    @GET
    @Path("/find/name")
    public Response findByUserName(@QueryParam("username") String username) {
        return Response.ok().entity(username).build();

    }

    @POST
    public Response createUser(String body) {
        // Test
        System.out.println("body: " + body);
        return Response.ok().build();
    }

    @PUT
    public Response updateUser(String body) {
        return Response.ok().entity("ToUpdateUser: " + body).build();
    }

    @DELETE
    public Response deleteUser(@QueryParam("id") int id) {
        return Response.ok().entity("Deleteing user " + id).build();
    }

}
