package entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "address")
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String additionalInfo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "address")
    private List<Person> persons;

    public Address(String street, String additionalInfo, List<Person> persons) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.persons = persons;
    }

    public Address() {
    }


    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

}