package facades;

import dtos.AddressDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import org.apache.maven.model.License;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }


    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {

        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    public PersonDTO createPerson(PersonDTO p) throws Exception {
        if (p.getFirstName() == null || p.getLastName() == null ) {
            throw new Exception("Fields are missing!");
        }
        Person person = new Person(p.getFirstName(), p.getLastName());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    public PersonDTO getSinglePerson(int id) throws Exception {
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null) {
            throw new Exception("Person id NOT found");
        } else {
            return new PersonDTO(p);
        }
    }

    public PersonDTO updatePerson(PersonDTO p) throws Exception {
        if (p.getFirstName() == null || p.getLastName() == null) {
            throw new Exception("One or more fields are missing!");
        }
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person tempPerson = em.find(Person.class, p.getId());
            tempPerson = tempPerson.dtoPerson(p);
            em.getTransaction().commit();
            return new PersonDTO(tempPerson);
        } finally {
            em.close();
        }
    }

    public PersonDTO deletePerson(int id) throws Exception {
        EntityManager em = emf.createEntityManager();
        Person person;
        try {
            em.getTransaction().begin();
            person = em.find(Person.class, id);
            if (person == null) {
                throw new Exception("Could not delete, Person id does not exist!");
            }
            em.remove(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = query.getResultList();
        return PersonDTO.getDtos(persons);
    }

    public List<PersonDTO> getAllPersonsWithHobby (HobbyDTO h) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT h.persons FROM Hobby h WHERE h.name = :name", Person.class);
        query.setParameter("name",h.getName());
        List<Person> persons = query.getResultList();
        return PersonDTO.getDtos(persons);
    }

    public List<PersonDTO> getAllPersonInCity(PersonDTO p) {

        return null;
    }

    public List<PersonDTO> getAllPersonsHobbies(PersonDTO p) {
        return null;
    }

    public List<AddressDTO> getAllZipcodes(PersonDTO p) {
        return null;
    }

}
