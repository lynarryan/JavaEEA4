package rest;

import java.security.Principal;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ejb.UserManager;
import models.BlogUser;
import models.PlatformUser;

@Path("/user")
public class UserApi {

    @EJB
    UserManager userBean;

    @GET
    @Path("/find/id")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByID(@QueryParam("id") int id, SecurityContext sc) {

        Principal p = sc.getCallerPrincipal();
        if (p == null) {
            return Response.serverError().entity("{\"message\":\"missing principal\"}").build();
        } else {
            PlatformUser pUser = (PlatformUser) p;
            BlogUser result = userBean.find(id);
            if (pUser.getId() == result.getId()) {
                if (result != null) {
                    return Response.ok(result).build();
                }
            }
        }
        return Response.status(403).entity("UNAUTHORIZED ACCESS").build();
    }

    @GET
    @Path("/find/name")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByUserName(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName) {
        BlogUser result = userBean.find(firstName, lastName);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(404).entity("Unable to find a user with provided first and last names").build();
        }
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
        List<BlogUser> result = userBean.list();
        if (result.isEmpty()) {
            return Response.ok().entity(result).build();
        } else {
            return Response.status(404).entity("Unable to construct a list of users").build();
        }
    }

}
