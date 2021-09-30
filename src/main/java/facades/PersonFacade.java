package facades;

import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.CityInfo;
import entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
        if (p.getFirstName() == null || p.getLastName() == null) {
            throw new Exception("Fields are missing!");
        }
        EntityManager em = emf.createEntityManager();
        try {
            CityInfo ci = searchZips(p.getAddress().getCityInfoDTO().getZipcode());
            if (ci != null) {
                CityInfoDTO c = new CityInfoDTO(ci);
                p.getAddress().setCityInfoDTO(c);
            }
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        Person person = new Person(p);
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }


    public CityInfo searchZips(String zipcode) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c where c.zipcode = :zipcode", CityInfo.class);
            query.setParameter("zipcode", zipcode);
            List<CityInfo> c = query.getResultList();
            if (c.size() > 0) {
                return c.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }


    public PersonDTO getSinglePerson(long id) throws Exception {
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
            //Person tempPerson = em.find(Person.class, p.getId());
            //tempPerson = tempPerson.dtoPerson(p);
            Person person = new Person(p);
            em.merge(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO deletePerson(long id) throws Exception {
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
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> persons = query.getResultList();
            return PersonDTO.getDtos(persons);
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getAllPersonsWithHobby(HobbyDTO h) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT h.persons FROM Hobby h WHERE h.name = :name", Person.class);
            query.setParameter("name", h.getName());
            List<Person> persons = query.getResultList();
            return PersonDTO.getDtos(persons);
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getAllPersonInCity(CityInfoDTO c) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.city = :city", Person.class);
            query.setParameter("city", c.getCity());
            List<Person> personList = query.getResultList();
            return PersonDTO.getDtos(personList);
        } finally {
            em.close();
        }
    }

    public List<CityInfoDTO> getAllZipcodes() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
            List<CityInfo> zipcodes = query.getResultList();
            return CityInfoDTO.getDtos(zipcodes);
        } finally {
            em.close();
        }
    }

}
