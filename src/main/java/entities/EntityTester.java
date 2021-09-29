package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class EntityTester {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        List<Phone> phones = new ArrayList<>();
        List<Hobby> hobbies = new ArrayList<>();

        Address address1 = new Address("Bøgevej","Lige nede af vejen lol");

        Person person1 = new Person("HEY","TEST",address1);

        phones.add(new Phone(1616, "description",person1));
        phones.add(new Phone(8888, "description",person1));
        hobbies.add(new Hobby("svømning","det vådt"));
        hobbies.add(new Hobby("løb", "nederen"));

        person1.setHobbys(hobbies);



        person1.setAddress(address1);

        try {
            em.getTransaction().begin();
            em.persist(person1);
            em.getTransaction().commit();
            //Verify database id
            System.out.println(person1.getFirstName());

        }finally{
            em.close();
        }
    }
}
