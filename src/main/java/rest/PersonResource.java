package rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/hello-world")
public class PersonResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }






    public Response getAllPersons(){

        return null;
    }
}