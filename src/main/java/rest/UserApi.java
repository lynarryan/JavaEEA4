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
import ejb.UserManager;

@Path("/user")
public class UserApi {

    @GET
    @Path("/find/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByID(@QueryParam("id") int id) {
        // TODO Find by ID + Behaviour
        return Response.ok().entity("" + id).build();
    }

    @GET
    @Path("/find/name")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByUserName(@QueryParam("username") String username) {
        return Response.ok().entity(username).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String body) {
        // Test
        System.out.println("body: " + body);
        return Response.ok().build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(String body) {
        return Response.ok().entity("ToUpdateUser: " + body).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@QueryParam("id") int id) {
        return Response.ok().entity("Deleteing user " + id).build();
    }

    @GET
    @Path("/list")
    public Response listUsers() {
        return Response.ok().entity("Returning a list of users").build();
    }

}
