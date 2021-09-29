package entities;

import com.google.gson.Gson;
import dtos.PersonDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class EntityTester {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
//        List<Phone> phones = new ArrayList<>();
//        List<Hobby> hobbies = new ArrayList<>();
//
        Address a1 = new Address("Grønnegade","Den gode vej");
        Address a2 = new Address("Kvinde","Jaja");

        Person p1 = new Person("AUgust","Manniche");
        Person p2 = new Person("Søren", "Med");

        a1.addPersons(p1);
        a2.addPersons(p2);

        Phone phone1 = new Phone(2321123,"Augusts");
        Phone phone2 = new Phone(32112312,"Sørens");

        p1.addPhone(phone1);
        p2.addPhone(phone2);
//
//        phones.add(new Phone(1616, "description"));
//        phones.add(new Phone(8888, "description"));
//        hobbies.add(new Hobby("svømning","det vådt"));
//        hobbies.add(new Hobby("løb", "nederen"));
//
//        person1.addHobby(hobbies.get(0));
//        person1.addHobby(hobbies.get(1));
//        person1.addPhone(phones.get(0));
//        person1.addPhone(phones.get(1));
//
//
//
//        person1.setAddress(address1);

        PersonDTO personDTO = new PersonDTO(p1);
        System.out.println(new Gson().toJson(personDTO));
        try {
            em.getTransaction().begin();
//            em.persist(person1);
            em.persist(a1);
            em.persist(a2);
            em.getTransaction().commit();
//            //Verify database id
//            System.out.println(person1.getFirstName());

        }finally{
            em.close();
        }
    }
}
