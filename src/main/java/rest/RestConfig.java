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

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(UserApi.class);
        resources.add(BlogApi.class);
        resources.add(PostApi.class);
        return resources;
    }

    @GET
    public Response simpleTest() {
        return Response.ok().build();
    }

}
