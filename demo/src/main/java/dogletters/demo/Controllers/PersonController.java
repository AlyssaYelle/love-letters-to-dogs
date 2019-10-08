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

    // log in user
    // returns a jwt token
//    {
//        "username" : "their username",
//        "password" : "their password"
//    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Person person) {
        return ResponseEntity.ok(new JwtResponse(personService.login(person)));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/list")
    public Iterable<Person> listPeople() {
        return personService.listPeople();
    }

    // sign up user
    // returns a jwt token
//    {
//        "username" : "a username",
//        "password" : "a password",
//        "email" : "an email address",
//        "userRole" : {
//            "name" : "ROLE NAME"
//        }
//    }
    @PostMapping("/signup")
    public ResponseEntity<?> createPerson(@RequestBody Person newPerson) {
        return ResponseEntity.ok(new JwtResponse(personService.createPerson(newPerson)));
    }

    // i think this just logs out the user??
    // TODO
    // test this in postman
    @DeleteMapping("/user/{userId}")
    public HttpStatus deleteUserById(@PathVariable Long id) {
        return personService.deleteById(id);
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!!";
    }

    // returns array of posts by user
    @GetMapping("/user/{username}/posts")
    public Iterable<Post> listUserPosts(@PathVariable String username){
        return personService.listPersonPosts(username);
    }
    // response looks like:
//    [
//        {
//            "id": 1,
//            "title": "Sheena",
//            "content": "Sheena is great dog and I hope she is adopted very soon!"
//        },
//        {
//        "id": 2,
//            "title": "Friday",
//            "content": "Friday is a bouncy boye! A very good boye!"
//        }
//    ]

    @GetMapping("/user/{username}/comments")
    public Iterable<Comment> listUserComments(@PathVariable String username){
        return personService.listPersonComments(username);
    }
}
