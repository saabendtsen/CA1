package entities;

import dtos.HobbyDTO;
import dtos.PhoneDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NamedQuery(name = "Hobby.deleteAllRows", query = "DELETE from Hobby")
@Table(name = "HOBBY", uniqueConstraints={@UniqueConstraint(columnNames ={"ID","NAME"})})
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Person> persons;

    public Hobby() {
    }


    public Hobby(String name, String description, List<Person> persons) {
        this.name = name;
        this.description = description;
        this.persons = persons;
    }

    public Hobby(String name, String description) {
        this.name = name;
        this.description = description;
        this.persons = new ArrayList<>();
    }

    public Hobby(HobbyDTO hDTO) {
        if (hDTO.getId() != null) {
            this.id = hDTO.getId();
        }
        this.name = hDTO.getName();
        this.description = hDTO.getDescription();
        this.persons = new ArrayList<>();
    }

    public List<Person> addPersons(Person person) {
        this.persons.add(person);
        return persons;
    }

    public List<Person> getPersons() {
        return persons;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Hobby> toDtos(List<HobbyDTO> hobbies) {
        List<Hobby> hobbyList = new ArrayList();
        for (HobbyDTO pDTO : hobbies) {
            hobbyList.add(new Hobby(pDTO));
        }
        return hobbyList;

    }
}