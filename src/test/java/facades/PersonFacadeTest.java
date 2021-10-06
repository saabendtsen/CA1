package facades;

import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.*;
import errorhandling.MissingFieldsException;
import errorhandling.PersonNotFoundException;
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
    private final PersonFacade facade = PersonFacade.getPersonFacade(emf);

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();

    }

    @AfterAll
    public static void tearDownClass() {

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

            person = new Person(facade.createPerson(new PersonDTO(person)));


        } catch (MissingFieldsException e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

//    @AfterEach
//    void tearDown() {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
////            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
////            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }

    @Test
    void createPerson() throws Exception {
        CityInfo cityInfo = new CityInfo("335","Younes");
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
    //@Test
    void updatePerson() throws Exception {
        Address newA = new Address("New Address", "new Address",info);
        Hobby newH = new Hobby("Bagning","ovnen");
        person.addHobby(newH);
        person.setAddress(newA);
        Phone ph = new Phone(12312313,"");
        person.addPhone(ph);
        person.getPhone().remove(0);

        PersonDTO personDTO = facade.updatePerson(new PersonDTO(person));

        assertEquals(person.getAddress().getStreet(), personDTO.getAddress().getStreet());
        assertEquals(person.getHobbies().get(0).getName(),personDTO.getHobbies().get(0).getName());
        assertEquals(person.getPhone().get(0).getNumber(),personDTO.getPhones().get(0).getNumber());
    }

    @Test
    void deletePerson() throws Exception {
        PersonDTO personDTO = facade.deletePerson(person.getId());
        assertEquals(personDTO.getId(), person.getId());
    }

    @Test
    void getAllPersonsWithHobby() throws PersonNotFoundException {
        List<PersonDTO> personDTOS = facade.getAllPersonsWithHobby(hobby.getName());
        assertEquals(person.getFirstName(), personDTOS.get(0).getFirstName());
    }

    @Test
    void getAllPersonInCity() throws MissingFieldsException {
        List<PersonDTO> personDTOS = facade.getAllPersonInZipcode(person.getAddress().getCityInfo().getZipcode());
        assertEquals(person.getFirstName(), personDTOS.get(0).getFirstName());
    }


    @Test
    void getAllZipcodes() {
        List<CityInfoDTO> cityInfos = facade.getAllZipcodes();
        assertEquals(cityInfos.size(), 1352);
    }


}