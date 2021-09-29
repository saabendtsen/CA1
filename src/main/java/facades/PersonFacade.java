package facades;

import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {}

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getPersonFacade(emf);
    }

    public void addPerson() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //em.persist();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        //return new PersonDTO();
    }

    public void editPerson() {
        EntityManager em = emf.createEntityManager();
        try {
            Person pp = em.find(Person.class, p.getId());
            pp.setFirstName(p.getfName());
            pp.setLastName(p.getlName());
            pp.setPhone(p.getPhone());
            pp.setLastEdited(new Date());
            em.getTransaction().begin();
            em.merge(pp);
            em.getTransaction().commit();
            return p;
        } finally {
            em.close();
        }
    }

}
