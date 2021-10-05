package rest;

import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.*;
import errorhandling.MissingFieldsException;
import facades.PersonFacade;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";


    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private final PersonFacade pf = PersonFacade.getPersonFacade(emf);

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    private Person person;
    private Address address;
    private Phone phone;
    private Hobby hobby;
    private CityInfo info;
    private Person person1;
    private Address address1;
    private Phone phone1;
    private Hobby hobby1;
    private CityInfo info1;

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            info = new CityInfo("960", "LOoool");
            address = new Address("Gågaden 5", "Til venstre for gelædneret", info);
            person = new Person("Niller", "Hassan", address);
            hobby = new Hobby("Skydning", "Pow pow pow");
            person.addHobby(hobby);
            phone = new Phone(75849232, "Nokiephone");
            person.addPhone(phone);


            info1 = new CityInfo("1730", "København K");
            address1 = new Address("Stenstregen 3", "Det kolde nord", info);
            person1 = new Person("Karsten", "Abdula", address);
            hobby1 = new Hobby("Skydning", "pow pow pow");
            person1.addHobby(hobby);
            phone1 = new Phone(75823232, "Motorola fra 89");
            person1.addPhone(phone);


//            em.getTransaction().begin();
//            em.persist(person);
//            em.getTransaction().commit();

            person = new Person(pf.createPerson(new PersonDTO(person)));
            person1 = new Person(pf.createPerson(new PersonDTO(person1)));

        } catch (MissingFieldsException e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            // em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            // em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }

    @Test
    void getAllPersons() {
        List<PersonDTO> personDTOS;
        personDTOS = given()
                .contentType(ContentType.JSON)
                .get("/person")
                .then()
                .extract().body().jsonPath().getList("", PersonDTO.class);

        assertThat(personDTOS, hasSize(2));
    }


    @Test
    void createPerson() {
    }

    @Test
    void getSinglePerson() {
        given()
                .contentType("application/json")
                .get("/person/" + person.getId() ).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(new Long(person.getId()).intValue()));
    }

    @Test
    void updatePerson() {
    }

    @Test
    void deletePerson() {
    }

    @Test
    void getAllPersonsWithHobby() {
        given()
                .contentType(ContentType.JSON)
                .get("/person/hobby/" + hobby.getName()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstName", hasItem(person1.getFirstName()))
                .body("lastName", hasItem(person1.getLastName()))
                .body("id", notNullValue());
    }

    @Test
    void getAllPersonInCity() {
        given()
                .contentType(ContentType.JSON)
                .get("/person/zipcode/" + person.getAddress().getCityInfo().getZipcode())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstName", hasItem(person.getFirstName()))
                .body("lastName", hasItem(person.getLastName()))
                .body("id", notNullValue());
    }

    @Test
    void getAllZipcodes() {
        List<CityInfoDTO> cityInfoDTOList;
        cityInfoDTOList = given()
                .contentType(ContentType.JSON)
                .get("/person/zipcodes")
                .then()
                .extract().body().jsonPath().getList("", CityInfoDTO.class);

        assertThat(cityInfoDTOList, hasSize(1352));
    }
}