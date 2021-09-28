package entities;

import javax.persistence.*;
import java.util.List;

@Table(name = "hobby")
@Entity
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany
    private List<Person> persons;


    public Hobby(String name, String description) {
        this.name = name;
        this.description = description;
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

    public Hobby() {
    }

    public Hobby(String name, String description, List<Person> persons) {
        this.name = name;
        this.description = description;
        this.persons = persons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}