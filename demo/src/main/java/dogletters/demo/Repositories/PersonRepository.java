package dogletters.demo.Repositories;

import dogletters.demo.Models.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    // to login i need info: username & password
    // query will find that record in db
    @Query("FROM Person p WHERE p.username = ?1 and p.password = ?2")
    public Person login(String username, String password);

    // find a person by their unique username
    public Person findByUsername(String username);
}
