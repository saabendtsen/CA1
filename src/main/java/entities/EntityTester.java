package entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        List<Hobby> hobbies = new ArrayList<>();

        Address address1 = new Address("Bøgevej", "Lige nede af vejen lol");

        Person person1 = new Person("HEY", "TEST", address1);
        Person person2 = new Person("HEY2", "TEST2", address1);

        phones.add(new Phone(1616, "description"));
        phones.add(new Phone(8888, "description"));
        hobbies.add(new Hobby("svømning", "det vådt"));
        hobbies.add(new Hobby("løb", "nederen"));

        CityInfo cityInfo1 = new CityInfo("3730","Nexø");
        cityInfo1.addAddress(address1);
        person1.addHobby(hobbies.get(0));
        person2.addHobby(hobbies.get(1));
        person1.addPhone(phones.get(0));
        person1.addPhone(phones.get(1));


        person1.setAddress(address1);

//        PersonDTO pDTO = new PersonDTO(person1);
        //System.out.println(gson.toJson(pDTO));

        try {
            em.getTransaction().begin();
            em.persist(person1);
            em.persist(person2);
            em.persist(cityInfo1);
            em.getTransaction().commit();
            //Verify database id


            PersonFacade pf = PersonFacade.getPersonFacade(emf);


            System.out.println(gson.toJson(pf.getAllPersonsWithHobby(new HobbyDTO(hobbies.get(1)))));


        } finally {
            em.close();
        }
    }
}
