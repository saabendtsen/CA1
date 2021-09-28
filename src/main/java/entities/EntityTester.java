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

        Person person1 = new Person("HEY","TEST",phones);

        phones.add(new Phone(1616, "description",person1));


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
