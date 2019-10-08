package dogletters.demo.Repositories;

import dogletters.demo.Models.Person;
import dogletters.demo.Models.PersonRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRoleRepository extends CrudRepository<PersonRole, Integer> {

    public PersonRole findByName(String name);

    // find all users who have given role
    public Iterable<Person> findAllByName(String name);
}
