package dogletters.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="person_role")
public class PersonRole {
    // Creates primary key unique ID column and
    // auto-generates an id with each new User role
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Creates column for name of role
    @Column(unique = true)
    private String name;

    // link table to person
    @OneToMany(mappedBy = "personRole",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Person> people;


    public PersonRole(){}

    public void addPerson(Person person){
        // create new list if necessary
        if(people == null)
            people = new ArrayList<>();
        people.add(person);
        person.setPersonRole(this);
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Person> getPeople() {
        return people;
    }

    // column get methods

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    // column set methods

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
