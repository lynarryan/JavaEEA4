/**********************************************************************egg*m******a******n********************
 * File: UserApi.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package rest;

import java.security.Principal;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
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
import ejb.UserManager;
import models.BlogUser;
import models.PlatformUser;

@Path("/user")
public class UserApi {

    @EJB
    UserManager userBean;

    @Context
    protected SecurityContext sc;

    /**
     * Find user by id
     * @param id
     * @return JSON response
     */
    @GET
    @Path("/find/id")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByID(@QueryParam("id") int id) {

        BlogUser result = userBean.find(id);
        if (result != null) {
            return Response.ok(result).build();
        }else {
            return Response.status(404).entity("Unable to find a user with provided id").build();
        }
    }

    /**
     * Find user bu username
     * @param firstName
     * @param lastName
     * @return JSON response
     */
    @GET
    @Path("/find/name")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByUserName(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName) {
        BlogUser result = userBean.find(firstName, lastName);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(404).entity("Unable to find a user with provided first and last names").build();
        }
    }

    /**
     * Create user
     * @param body
     * @return JSON response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response createUser(BlogUser body) {
        userBean.create(body);
        return Response.ok().build();

    }

    /**
     * Update user
     * @param toUpdate
     * @return JSON response
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response updateUser(BlogUser toUpdate) {
        userBean.update(toUpdate);
        return Response.ok().build();
    }

    /**
     * Delete a user
     * @param id
     * @return JSON response
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response deleteUser(@QueryParam("id") int id) {
        userBean.delete(id);
        return Response.ok().build();
    }

    /**
     * Get list of users
     * @return JSON response
     */
    @GET
    @Path("/list")
    @RolesAllowed("ADMIN")
    public Response listUsers() {
        List<BlogUser> result = userBean.list();
        if (result.isEmpty()) {
            return Response.ok().entity(result).build();
        } else {
            return Response.status(404).entity("Unable to construct a list of users").build();
        }
    }

}
