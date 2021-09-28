/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.RenameMeDTO;
import entities.Hobby;
import entities.Person;
import entities.RenameMe;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

import java.util.List;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);

        List<Person> personList = null;

        Hobby h1 = new Hobby("Svømning","det er i vand");


    }
    
    public static void main(String[] args) {
        populate();
    }
}
