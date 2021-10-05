package entities;

import dtos.AddressDTO;
import dtos.CityInfoDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "address")
@Entity
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String additionalInfo;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "address")
    private List<Person> persons;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private CityInfo cityInfo;

    public Address() {
    }


    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public Address(String street, String additionalInfo, CityInfo cityInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.persons = new ArrayList<>();
        this.cityInfo = cityInfo;
    }

    public Address(AddressDTO address) {
        if (address.getId() != null){
            this.id = address.getId();
        }
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();
        this.cityInfo = new CityInfo(address.getCityInfoDTO());
    }


    public List<Person> getPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
        if(person != null){
            person.setAddress(this);
        }
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