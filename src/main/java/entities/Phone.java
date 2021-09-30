package entities;

import dtos.PhoneDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "phone")
@Entity
@NamedQuery(name = "Phone.deleteAllRows", query = "DELETE from Phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    private String description;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Person person;

    public Phone() {
    }

    public Phone(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public Phone(PhoneDTO phoneDTO) {
        this.id = phoneDTO.getId();
        this.number = phoneDTO.getNumber();
        this.description = phoneDTO.getDescription();
    }

    public List<Phone> toDtos(List<PhoneDTO> phoneDTOList){
        List<Phone> phoneList = new ArrayList();
        for (PhoneDTO pDTO: phoneDTOList) {
            phoneList.add(new Phone(pDTO));
        }
        return phoneList;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}