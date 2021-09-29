package facades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import entities.Person;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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


    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getPersonFacade(emf);
    }

    public PersonDTO createPerson(PersonDTO p) {
        return null;
    }

    public PersonDTO readPerson(PersonDTO p) {
        return null;
    }

    public PersonDTO updatePerson(PersonDTO p) {
        return null;
    }

    public PersonDTO deletePerson(PersonDTO p) {
        return null;
    }

    public List<PersonDTO> getAllPersonsWithHobby (PersonDTO p) {

        return null;
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
