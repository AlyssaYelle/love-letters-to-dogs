package dogletters.demo.Controllers;

import dogletters.demo.Models.Person;
import dogletters.demo.Models.PersonRole;
import dogletters.demo.Services.PersonRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class PersonRoleController {
    @Autowired
    PersonRoleService roleService;

    @GetMapping("/{rolename}")
    public Iterable<Person> getRole(@PathVariable String rolename) {
        return roleService.ListAllUsersWithRoleAuth(rolename);
    }

    @PostMapping("/createRole")
    public PersonRole createRole(@RequestBody PersonRole role) {

        System.out.println("creating user role");
        return roleService.createRole(role);
    }
}
