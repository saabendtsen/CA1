package facades;

import dtos.CityInfoDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.*;
import errorhandling.MissingFieldsException;
import errorhandling.PersonNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
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

    //
    public PersonDTO createPerson(PersonDTO p) throws MissingFieldsException {
        if (p == null) {
            throw new MissingFieldsException("Fields are missing!");
        }
        EntityManager em = emf.createEntityManager();
        Person person = new Person(p);
        try {
            CityInfo ci = searchZips(p.getAddress().getCityInfoDTO().getZipcode(), em);
            if (ci != null) {
                person.getAddress().setCityInfo(ci);
            }

            person.setPhone(new ArrayList<>());

            for (PhoneDTO pDTO : p.getPhones()) {
                person.addPhone(new Phone(pDTO));
            }

            person.setHobbies(new ArrayList<>());
            for (int i = 0; i < p.getHobbies().size(); i++) {
                Hobby h = searchHobbys(p.getHobbies().get(i).getName(), em);
                if (h != null) {
                    person.addHobby(h);
                } else {
                    person.addHobby(new Hobby(p.getHobbies().get(i)));
                }
            }
        } catch (Exception e) {
            throw new MissingFieldsException(e.getMessage());
        }

        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }


    public Hobby searchHobbys(String hobby, EntityManager em) throws MissingFieldsException {
        if (!hobby.equals("")) {
            try {
                TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h where h.name = :name", Hobby.class);
                query.setParameter("name", hobby);
                List<Hobby> h = query.getResultList();
                if (h.size() > 0) {
                    return h.get(0);
                } else {
                    return null;
                }
            } finally {

            }
        } else {
            throw new MissingFieldsException("Missing Hobby!");
        }
    }

    public CityInfo searchZips(String zipcode, EntityManager em) throws MissingFieldsException {
        if (!zipcode.equals("")) {
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

            }
        } else {
            throw new MissingFieldsException("Missing Hobby!");

        }
    }


    public PersonDTO getSinglePerson(long id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null) {
            throw new PersonNotFoundException("Person id NOT found");
        } else {
            return new PersonDTO(p);
        }
    }

    public PersonDTO updatePerson(PersonDTO p) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        if (p != null) {
            Person entityPerson = em.find(Person.class, p.getId());
            entityPerson.setFirstName(p.getFirstName());
            entityPerson.setLastName(p.getLastName());
            entityPerson.setAddress(new Address(p.getAddress()));
            entityPerson.setPhone(new ArrayList<>());

            for (PhoneDTO pDTO : p.getPhones()) {
                entityPerson.addPhone(new Phone(pDTO));
            }

            try {

                CityInfo ci = searchZips(p.getAddress().getCityInfoDTO().getZipcode(), em);
                if (ci != null) {
                    entityPerson.getAddress().setCityInfo(ci);
                }

                List<Hobby> loopList = entityPerson.getHobbies();
                for (int i = 0; i < p.getHobbies().size(); i++) {
                    Hobby h = searchHobbys(p.getHobbies().get(i).getName(), em);
                    if (h != null) {
                        for (int j = 0; j < loopList.size(); j++) {
                            Hobby eh = loopList.get(j);
                            if (eh == h) {
                                continue;
                            } else {
                                entityPerson.addHobby(h);
                            }
                        }
                    } else {
                        entityPerson.addHobby(new Hobby(p.getHobbies().get(i)));
                    }
                }
            } catch (Exception e) {
                throw new PersonNotFoundException(e.getMessage());
            }

            try {
                em.getTransaction().begin();
                em.merge(entityPerson);
                em.getTransaction().commit();
                return new PersonDTO(entityPerson);
            } finally {
                em.close();
            }
        } else {
            throw new PersonNotFoundException("Person not found!");
        }
    }

    public PersonDTO deletePerson(long id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException("Person with id: "+id+" does not exist");
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

    public List<PersonDTO> getAllPersonsWithHobby(String name) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p from Person p join p.hobbies h WHERE h.name = :name", Person.class);
            query.setParameter("name", name);
            List<Person> persons = query.getResultList();
            if (persons.size() == 0) {
                throw new PersonNotFoundException("No persons with hobby: "+name+" found!");
            }
            return PersonDTO.getDtos(persons);
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getAllPersonInZipcode(String zipcode) throws MissingFieldsException {
        EntityManager em = emf.createEntityManager();
        try {
            if (zipcode.equals("")) {
                throw new MissingFieldsException("Zipcode field is missing!");
            }
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.zipcode = :zipcode", Person.class);
            query.setParameter("zipcode", zipcode);
            List<Person> personList = query.getResultList();
            if (personList.size() == 0) {
                throw new MissingFieldsException("No persons living in: "+zipcode+" found");
            }
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