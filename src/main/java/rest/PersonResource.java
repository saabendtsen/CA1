package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Person;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/person")
public class PersonResource {
    private final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final PersonFacade pf = PersonFacade.getPersonFacade(EMF);

    @GET
    @Path("all")
    @Produces("application/json")
    public String getAllPersons(){
        List<PersonDTO> personDTOList = pf.getAllPersons();

        return GSON.toJson(personDTOList);
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public String createPerson(String person) throws Exception {
        PersonDTO personDTO = GSON.fromJson(person,PersonDTO.class);
        personDTO = pf.createPerson(personDTO);
        return GSON.toJson(personDTO);
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
    public String updatePerson(String person) throws Exception {
        PersonDTO personDTO = GSON.fromJson(person,PersonDTO.class);
        personDTO = pf.updatePerson(personDTO);

        return GSON.toJson(personDTO);
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public String deletePerson(@PathParam("id")long id) throws Exception {
        PersonDTO personDTO = pf.deletePerson(id);

        return GSON.toJson(personDTO);
    }

    @GET
    @Path("{hobby}")
    @Produces("application/json")
    public String getAllPersonsWithHobby(@PathParam("hobby")String hobby){
        HobbyDTO hobbyDTO = GSON.fromJson(hobby,HobbyDTO.class);
        List<PersonDTO> personDTOS = pf.getAllPersonsWithHobby(hobbyDTO);

        return GSON.toJson(personDTOS);
    }

    @GET
    @Produces("application/json")
    public String getAllPersonInCity(String person){
        return null;
    }

    @GET
    @Produces("application/json")
    public Response getAllZipcodes(){
        return null;
    }
}