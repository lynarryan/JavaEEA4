/**********************************************************************egg*m******a******n********************
 * File: RestConfig.java
 * Course materials (19W) CST 8277
 * @author (student) Ryan Lynar 040-879-248
 * @author (student) Lauren Preston 040-839-284
 * @author (student) Gregory Leverton 040-885-599
 * 
 */
package rest;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@ApplicationPath("")
@DeclareRoles({ "USER", "ADMIN" })
@OpenAPIDefinition(info = @Info(title = "a4", version = "1.0"), servers = { @Server(url = "/a4") })
public class RestConfig extends Application {

    
    /* (non-Javadoc)
     * @see javax.ws.rs.core.Application#getClasses()
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(UserApi.class);
        resources.add(BlogApi.class);
        resources.add(PostApi.class);
        resources.add(PlatformUserApi.class);
        resources.add(CommentApi.class);
        return resources;
    }

    /**
     * Get test
     * @return JSON response
     */
    @GET
    public Response simpleTest() {
        return Response.ok().build();
    }

}
