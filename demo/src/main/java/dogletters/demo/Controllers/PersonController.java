package dogletters.demo.Controllers;

import dogletters.demo.Models.Comment;
import dogletters.demo.Models.JwtResponse;
import dogletters.demo.Models.Person;
import dogletters.demo.Models.Post;
import dogletters.demo.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Person person) {
        return ResponseEntity.ok(new JwtResponse(personService.login(person)));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/list")
    public Iterable<Person> listPeople() {
        return personService.listPeople();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createPerson(@RequestBody Person newPerson) {
        return ResponseEntity.ok(new JwtResponse(personService.createPerson(newPerson)));
    }

    @DeleteMapping("/user/{userId}")
    public HttpStatus deleteUserById(@PathVariable Long id) {
        return personService.deleteById(id);
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!!";
    }

    @GetMapping("/user/{username}/posts")
    public Iterable<Post> listUserPosts(@PathVariable String username){
        return personService.listPersonPosts(username);
    }

    @GetMapping("/user/{username}/comments")
    public Iterable<Comment> listUserComments(@PathVariable String username){
        return personService.listPersonComments(username);
    }
}
