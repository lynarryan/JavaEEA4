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
import ejb.UserManager;
import models.BlogUser;

@Path("/user")
public class UserApi {
    
    @EJB
    UserManager userBean;
    
    @GET
    @Path("/find/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByID(@QueryParam("id") int id) {
        // TODO Find by ID + Behaviour
        return Response.ok(userBean.find(id)).build();
    }

    @GET
    @Path("/find/name")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByUserName(@QueryParam("firstName") String firstName , @QueryParam("lastName") String lastName) {
        return Response.ok().entity(userBean.find(firstName, lastName)).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(BlogUser body) {
        userBean.create(body);
        return Response.ok().build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(BlogUser toUpdate) {
        userBean.update(toUpdate);
        return Response.ok().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@QueryParam("id") int id) {
        userBean.delete(id);
        return Response.ok().build();
    }

    @GET
    @Path("/list")
    public Response listUsers() {
        return Response.ok().entity(userBean.list()).build();
    }

}
