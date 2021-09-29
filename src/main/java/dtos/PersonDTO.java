package dtos;


import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private AddressDTO address;
    private List<PhoneDTO> phones;
    private List<HobbyDTO> hobbies;


    public PersonDTO(Person p) {
        this.id = p.getId();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.address =  new AddressDTO(p.getAddress());
        this.phones = PhoneDTO.getDtos(p.getPhone());
        this.hobbies = HobbyDTO.getDtos(p.getHobbies());
    }

    public static List<PersonDTO> getDtos(List<Person> p){
        List<PersonDTO> pdtos = new ArrayList();
        p.forEach(rm -> pdtos.add(new PersonDTO(rm)));
        return pdtos;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PersonDTO{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", address=").append(address);
        sb.append(", phones=").append(phones);
        sb.append(", hobbies=").append(hobbies);
        sb.append('}');
        return sb.toString();
    }
}
