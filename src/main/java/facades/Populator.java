/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.RenameMeDTO;
import entities.*;

import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);

        List<Person> personList = new ArrayList<>();

        Person person = new Person("August", "niller");
        personList.add(person);
        Hobby h1 = new Hobby("Svømning","det er i vand");
        //person.addHobby(h1);
        Phone p1 = new Phone(1235,"Nokia",person);
        //person.addPhone(p1);
        Address a1 = new Address("Bøgevej","Det er på hjørnet",personList);
        person.setAddress(a1);

        emf.createEntityManager().persist(person);

    }
    
    public static void main(String[] args) {
        populate();
    }
}
