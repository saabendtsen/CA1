package entities;

import dtos.PersonDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Phone> phone;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "persons")
    private List<Hobby> hobbies;

    public Person() {
    }


    public Person(String firstName, String lastName, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = new ArrayList<>();
        this.hobbies = new ArrayList<>();
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person dtoPerson(PersonDTO dto){
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Phone> getPhone() {
        return phone;
    }

    public void addPhone(Phone p) {
        if(p != null){
            p.setPerson(this);
            this.phone.add(p);
        }
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addHobby(Hobby hobby) {
        if (hobby != null){
            this.hobbies.add(hobby);
            hobby.getPersons().add(this);

        }
    }
}