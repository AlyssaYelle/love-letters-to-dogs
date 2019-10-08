package dogletters.demo.Services;

import dogletters.demo.Models.Person;
import dogletters.demo.Models.PersonRole;
import dogletters.demo.Repositories.PersonRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonRoleServiceImpl implements PersonRoleService {
    @Autowired
    PersonRoleRepository personRoleRepository;

    @Override
    public PersonRole createRole(PersonRole newRole) {
        return personRoleRepository.save(newRole);
    }

    @Override
    public PersonRole getRole(String roleName) {
        return personRoleRepository.findByName(roleName);
    }

    // find all users who have given role
    @Override
    public Iterable<Person> ListAllUsersWithRoleAuth(String roleName) {
        PersonRole personRole = getRole(roleName);
        return personRole.getPeople();
    }

}
