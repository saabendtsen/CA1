package entities;

import dtos.PersonDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person")
@NamedNativeQuery(name = "Person.resetAutoI", query = "ALTER table PERSON AUTO_INCREMENT = 1")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Phone> phone;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "persons")
    @JoinTable(name = "HOBBY")
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
        this.phone = new ArrayList<>();
        this.hobbies = new ArrayList<>();
    }

    public Person dtoPerson(PersonDTO dto) {
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
        if (p != null) {
            p.setPerson(this);
            this.phone.add(p);
        }
    }

    public void removePhone(Phone p) {
        if (p != null) {
            this.phone.remove(p);
            p.setPerson(null);
        }
    }

    public Person(PersonDTO personDTO) {
        if (personDTO.getId() != null) {
            this.id = personDTO.getId();
        }
        this.firstName = personDTO.getFirstName();
        this.lastName = personDTO.getLastName();
        this.address = new Address(personDTO.getAddress());
        this.phone = new Phone().toDtos(personDTO.getPhones());
        this.hobbies = new Hobby().toDtos(personDTO.getHobbies());
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address != null) {
            this.address = address;
        }
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }

    public void addHobby(Hobby hobby) {
        this.hobbies.add(hobby);
        if (hobby != null) {
            hobby.addPersons(this);
        }
    }
}