package rest;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ejb.PlatformUserManager;
import models.PlatformUser;

@Path("/PlatformUser")
public class PlatformUserApi {

    @EJB
    PlatformUserManager puBean;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response CreatePlatformUser(PlatformUser pU) {
        puBean.createUser(pU);
        return Response.ok().build();
    }

}
