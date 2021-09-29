package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "person")
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
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Hobby> hobbys;

    public Person() {
    }


    public Person(String firstName, String lastName, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = new ArrayList<>();
        this.hobbys = new ArrayList<>();
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
        if(this.phone == null){
            this.phone = new ArrayList<>();
        }
        this.phone.add(p);
    }

    public List<Hobby> getHobbys() {
        return hobbys;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setHobbys(List<Hobby> hobbys) {
        this.hobbys = hobbys;
    }
}