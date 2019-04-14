/**********************************************************************egg*m******a******n********************
 * File: PlatformUserApi.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
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


    /**
     * Create a PlatofrmUSer
     * @param pU
     * @return JSON response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response CreatePlatformUser(PlatformUser pU) {
        puBean.createUser(pU);
        return Response.ok().build();
    }

}
