package facades;

import dtos.PersonDTO;
import entities.*;
import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    public PersonFacadeTest(){
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
        try{
            Address a1 = new Address("hej","s");
            Person person = new Person("hej","hasd",a1);
            Hobby hobby = new Hobby("Skydning","Skyd Søren i dilleren");
            person.addHobby(hobby);
            Phone phone = new Phone(75849232,"Jojo");
            person.addPhone(phone);
            CityInfo cityInfo = new CityInfo("3730","Nexø");
            cityInfo.addAddress(a1);

            em.getTransaction().begin();
//            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.persist(cityInfo);
            em.persist(person);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createPerson() throws Exception {

        Address address1 = new Address("Bøgevej", "Lige nede af vejen lol");
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
        PersonDTO pDTO = facade.getSinglePerson(1);
        assertEquals(pDTO.getId(),1);
    }

    @Test
    void updatePerson() {
    }

    @Test
    void deletePerson() throws Exception {
        PersonDTO personDTO = facade.deletePerson(1);
        assertEquals(personDTO.getId(),1);
    }

    @Test
    void getAllPersonsWithHobby() {
    }

    @Test
    void getAllPersonInCity() {
    }

    @Test
    void getAllPersonsHobbies() {
    }

    @Test
    void getAllZipcodes() {
    }
}