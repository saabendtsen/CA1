package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import errorhandling.MissingFieldsException;
import errorhandling.PersonNotFoundException;
import facades.PersonFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/person")
public class PersonResource {
    private final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final PersonFacade pf = PersonFacade.getPersonFacade(EMF);

    @GET
    @Produces("application/json")
    public Response getAllPersons() {
        return Response.ok(gson.toJson(pf.getAllPersons()), "application/json").build();
    }

    @GET
    @Path("zipcodes")
    @Produces("application/json")
    public Response getAllZipcodes() {
        return Response.ok(gson.toJson(pf.getAllZipcodes()), "application/json").build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addPerson(String person) throws MissingFieldsException {
        PersonDTO dto = gson.fromJson(person, PersonDTO.class);
        dto = pf.createPerson(dto);
        return Response.ok(gson.toJson(dto), "application/json").build();
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response editPerson(String person) throws MissingFieldsException {
        PersonDTO dto = gson.fromJson(person, PersonDTO.class);
        dto = pf.updatePerson(dto);
        return Response.ok(gson.toJson(dto), "application/json").build();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response deletePerson(@PathParam("id") long id) throws PersonNotFoundException {
        PersonDTO dto = pf.deletePerson(id);
        return Response.ok("{\"status\" : \"removed: " + gson.toJson(dto) + "\"}", "application/json").build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getPersonById(@PathParam("id") long id) throws PersonNotFoundException {
        PersonDTO dto = pf.getSinglePerson(id);
        return Response.ok(gson.toJson(dto), "application/json").build();
    }

    @GET
    @Path("hobby/{hobby}")
    @Produces("application/json")
    public Response getAllPersonsWithHobby(@PathParam("hobby") String hobby) throws MissingFieldsException {
        HobbyDTO hobbyDTO = gson.fromJson(hobby, HobbyDTO.class);
        List<PersonDTO> dto = pf.getAllPersonsWithHobby(hobbyDTO);
        return Response.ok(gson.toJson(dto), "application/json").build();
    }

    @GET
    @Path("city/{city}")
    @Produces("application/json")
    public Response getAllPersonInCity(@PathParam("city") String city) throws MissingFieldsException {
        CityInfoDTO cityInfoDTO = gson.fromJson(city, CityInfoDTO.class);
        List<PersonDTO> dto = pf.getAllPersonInCity(cityInfoDTO);
        return Response.ok(gson.toJson(dto), "application/json").build();
    }
}