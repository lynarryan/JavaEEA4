package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/user/")
public class UserApi {
    
    
    
    @GET
    @Path("/find")  
    public Response findByID(@QueryParam("id") int id) {
        return Response.ok().entity(""+id).build();
    }
    
    @GET
    @Path("/find")
    public Response findByUserName(@QueryParam("userName")String userName) {
        System.out.println("userName");
      return Response.ok().entity(userName).build();
        
    }

    

}
