package rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
@ApplicationPath("")
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
