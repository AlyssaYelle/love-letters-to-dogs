package dogletters.demo.Services;

import dogletters.demo.Models.Person;
import dogletters.demo.Models.PersonRole;

public interface PersonRoleService {
    // create a new role
    public PersonRole createRole(PersonRole newRole);

    // get role
    public PersonRole getRole(String roleName);

    // list all users with given role name
    public Iterable<Person> ListAllUsersWithRoleAuth(String roleName);
}
