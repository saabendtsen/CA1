package dtos;

import entities.Person;
import entities.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneDTO {

    private Long id;
    private int number;
    private String description;
    private PersonDTO person;

    public PhoneDTO(Phone p) {
        this.id = p.getId();
        this.number = p.getNumber();
        this.description = p.getDescription();
    }

    public static List<PhoneDTO> getDtos(List<Phone> p){
        List<PhoneDTO> pdtos = new ArrayList();
        p.forEach(rm->pdtos.add(new PhoneDTO(rm)));
        return pdtos;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }
}
