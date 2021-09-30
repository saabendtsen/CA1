package facades;

import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonFacadeTest {
    private Person person;
    private Address address;
    private Phone phone;
    private Hobby hobby;
    private CityInfo info;

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            info = new CityInfo("3730", "Nexø");
            address = new Address("hej", "s", info);
            person = new Person("hej", "hasd", address);
            hobby = new Hobby("Skydning", "Skyd Søren i dilleren");
            person.addHobby(hobby);
            phone = new Phone(75849232, "Jojo");
            person.addPhone(phone);


            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();

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
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void createPerson() throws Exception {
        CityInfo cityInfo = new CityInfo("3212","Younes");
        Address address1 = new Address("Bøgevej", "Lige nede af vejen lol", cityInfo);
        Person person1 = new Person("Jens", "hansen");

        //address1.addPerson(person1);
        person1.setAddress(address1);

        Hobby h1 = new Hobby("svømning", "det vådt");
        person1.addHobby(h1);

        Phone p1 = new Phone(8888, "description");
        person1.addPhone(p1);


        PersonDTO actual = facade.createPerson(new PersonDTO(person1));
        assertEquals(actual.getFirstName(), person1.getFirstName());


    }

    @Test
    void readPerson() throws Exception {
        PersonDTO pDTO = facade.getSinglePerson(person.getId());
        assertEquals(pDTO.getId(), person.getId());
    }

    @Test
    void updatePerson() throws Exception {
        Address newA = new Address("New Address", "new Address",info);

        person.setAddress(newA);
        PersonDTO personDTO = facade.updatePerson(new PersonDTO(person));

        assertEquals(person.getAddress().getStreet(), personDTO.getAddress().getStreet());

    }

    @Test
    void deletePerson() throws Exception {
        PersonDTO personDTO = facade.deletePerson(person.getId());
        assertEquals(personDTO.getId(), person.getId());
    }

    @Test
    void getAllPersonsWithHobby() {
        HobbyDTO hobbyDTO = new HobbyDTO(hobby);
        List<PersonDTO> personDTOS = facade.getAllPersonsWithHobby(hobbyDTO);
        assertEquals(person.getFirstName(), personDTOS.get(0).getFirstName());
    }

    @Test
    void getAllPersonInCity() {
        CityInfoDTO cityInfoDTO = new CityInfoDTO(info);
        List<PersonDTO> personDTOS = facade.getAllPersonInCity(cityInfoDTO);
        assertEquals(person.getFirstName(), personDTOS.get(0).getFirstName());
    }


    @Test
    void getAllZipcodes() {
        List<CityInfoDTO> cityInfos = facade.getAllZipcodes();
        assertEquals(info.getZipcode(), cityInfos.get(0).getZipcode());
    }
}