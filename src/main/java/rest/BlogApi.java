package rest;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
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

import ejb.BlogManager;
import models.Blog;

@Path("/blog/")
public class BlogApi {

    @EJB
    BlogManager blogBean;

    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByID(@QueryParam("id") int id) {
        Blog result = blogBean.getBlogById(id);
        if (result != null) {
            return Response.ok().entity(blogBean.getBlogById(id)).build();
        } else {
            return Response.status(404).entity("Unable to find a blog with the provided id").build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBlog(Blog body) {
        blogBean.createBlog(body);
        return Response.ok().build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBlog(Blog body) {
        blogBean.updateBlog(body);
        return Response.ok().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBlog(@QueryParam("id") int id) {
        blogBean.deleteBlog(id);
        return Response.ok().build();
    }

    @GET
    @Path("/list")
    public Response listBlogs() {
        List<Blog> blogs = blogBean.getBlogList();
        if (!blogs.isEmpty()) {
            return Response.ok().entity(blogBean.getBlogList()).build();
        } else {
            return Response.status(404).entity("Unable to Construct a list of blogs").build();
        }

    }

}
