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

    @OneToMany
    private Phone phone;



    @ManyToMany
    private List<Hobby> hobbys;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}