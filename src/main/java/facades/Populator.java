/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate() throws Exception {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        PersonFacade pf = PersonFacade.getPersonFacade(emf);

        Person person;
        Address address;
        Phone phone;
        Hobby hobby;
        CityInfo info;

        info = new CityInfo("3730", "Nexø");
        address = new Address("hej", "s", info);
        person = new Person("hej", "hasd", address);
        hobby = new Hobby("Skydning", "Skyd Søren i dilleren");
        person.addHobby(hobby);
        phone = new Phone(75849232, "Jojo");
        person.addPhone(phone);

        em.persist(person);
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        em.close();
       //pf.createPerson(new PersonDTO(person));


    }
    
    public static void main(String[] args) throws Exception {
        populate();
    }
}
