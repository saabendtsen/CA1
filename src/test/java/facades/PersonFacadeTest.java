package facades;

import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

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
    void createPerson() {
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