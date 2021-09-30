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

        try {
            CityInfo info = new CityInfo("3730", "Nexø");
            Address address = new Address("hej", "s", info);
            Person person = new Person("hej", "hasd", address);
            Hobby hobby = new Hobby("Skydning", "Skyd Søren i dilleren");
            person.addHobby(hobby);
            Phone phone = new Phone(75849232, "Jojo");
            person.addPhone(phone);


            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();

        } finally {
            em.close();
        }


    }
}
