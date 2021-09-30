package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/person")
public class PersonResource {
    private final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final PersonFacade pf = PersonFacade.getPersonFacade(EMF);

    @GET
    @Produces("application/json")
    public Response getAllPersons(){

        return null;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response createPerson(){
        return null;
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getSinglePerson(@PathParam("id")long id) throws Exception {
        PersonDTO personDTO = pf.getSinglePerson(id);

        return GSON.toJson(personDTO);
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response updatePerson(){
        return null;
    }

    @DELETE
    @Produces("application/json")
    public Response deletePerson(){
        return null;
    }

    @GET
    @Produces("application/json")
    public Response getAllPersonsWithHobby(){
        return null;
    }

    @GET
    @Produces("application/json")
    public Response getAllPersonInCity(){
        return null;
    }

    @GET
    @Produces("application/json")
    public Response getAllZipcodes(){
        return null;
    }
}