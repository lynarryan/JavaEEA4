package rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ejb.PlatformUserManager;
import models.PlatformUser;

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
