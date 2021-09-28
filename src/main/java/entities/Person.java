package entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "person")
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @ManyToOne
    private Address address;

    @OneToMany(mappedBy = "person")
    private List<Phone> phone;

    @ManyToMany
    private List<Hobby> hobbys;

    public Person() {
    }

    public Person(String firstName, String lastName, Address address, List<Phone> phone, List<Hobby> hobbys) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.hobbys = hobbys;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhone() {
        return phone;
    }

    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }

    public List<Hobby> getHobbys() {
        return hobbys;
    }

    public void addHobby(Hobby hobby) {
        this.hobbys.add(hobby);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}