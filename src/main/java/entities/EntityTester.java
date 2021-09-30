package entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import facades.PersonFacade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class EntityTester {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        List<Phone> phones = new ArrayList<>();
        CityInfo cityInfo1 = new CityInfo("2730", "hej");
        Address address1 = new Address("Bøgevej", "Lige nede af vejen lol", cityInfo1);
        phones.add(new Phone(1616, "description"));
        phones.add(new Phone(8888, "description"));

        Person person1 = new Person("HEY", "TEST", address1);
        person1.addPhone(phones.get(0));
        person1.addPhone(phones.get(1));


        Person person2 = new Person("HEY2", "TEST2", address1);


        //PersonDTO pDTO = new PersonDTO(person1);
        //System.out.println(gson.toJson(pDTO));

        try {
            em.getTransaction().begin();
            em.persist(person1);
            em.persist(person2);
            em.getTransaction().commit();
            //Verify database id


//            PersonFacade pf = PersonFacade.getPersonFacade(emf);
//
//            // Get all persons in Person DB and print
//            System.out.println(gson.toJson(pf.getAllPersons()));
//            // Get all Persons with a Hobby and print
//            //System.out.println(gson.toJson(pf.getAllPersonsWithHobby(new HobbyDTO(hobbies.get(0)))));
//            // Get all Zipcodes
//            for (CityInfoDTO c : pf.getAllZipcodes()) {
//                if (c.getZipcode().equals("3000")) {
//                    System.out.println("City: " + c.getCity());
//                    System.out.println("Zipcode: " + c.getZipcode());
//                }
//            }

        } finally {
            em.close();
        }
    }
}
