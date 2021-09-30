package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import facades.PersonFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addPerson(String person) throws Exception {
        PersonDTO dto = gson.fromJson(person, PersonDTO.class);
        dto = pf.createPerson(dto);
        return Response.ok(gson.toJson(dto), "application/json").build();
    }

    @Path("{id}")
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response editPerson(@PathParam("id") long id, String person) throws Exception {
        PersonDTO dto = gson.fromJson(person, PersonDTO.class);
        dto.setId(id);
        dto = pf.updatePerson(dto);
        return Response.ok(gson.toJson(dto), "application/json").build();
    }

    @Path("{id}")
    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    public Response deletePerson(@PathParam("id") long id) throws Exception {
        PersonDTO dto = pf.deletePerson(id);
        return Response.ok("{\"status\" : \"removed: " + gson.toJson(dto) + "\"}", "application/json").build();
    }

    @Path("{id}")
    @GET
    @Produces("application/json")
    public Response getPersonById(@PathParam("id") long id) throws Exception {
        PersonDTO dto = pf.getSinglePerson(id);
        return Response.ok(gson.toJson(dto), "application/json").build();
    }

    @Path("hobby/{hobby}")
    @GET
    @Produces("application/json")
    public Response getAllPersonsWithHobby(@PathParam("hobby") String hobby) throws Exception {
        HobbyDTO hobbyDTO = gson.fromJson(hobby, HobbyDTO.class);
        List<PersonDTO> dto = pf.getAllPersonsWithHobby(hobbyDTO);
        return Response.ok(gson.toJson(dto), "application/json").build();
    }

    @Path("zipcodes")
    @GET
    @Produces("application/json")
    public Response getAllZipcodes() {
        return Response.ok(gson.toJson(pf.getAllZipcodes()), "application/json").build();
    }

    /*


    @Path("{hobby}")
    @GET
    @Produces("application/json")
    public String getAllPersonsWithHobby(@PathParam("hobby") String hobby) {
        HobbyDTO hobbyDTO = GSON.fromJson(hobby, HobbyDTO.class);
        List<PersonDTO> personDTOS = pf.getAllPersonsWithHobby(hobbyDTO);

        return GSON.toJson(personDTOS);
    }


    @Path("{city}")
    @GET
    @Produces("application/json")
    public String getAllPersonInCity(String person) {
        return null;
    }



*/
}