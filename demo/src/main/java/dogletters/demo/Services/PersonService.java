package dogletters.demo.Services;

import dogletters.demo.Models.Comment;
import dogletters.demo.Models.Person;
import dogletters.demo.Models.Post;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface PersonService extends UserDetailsService{
    // need to be able to get a person's info by their unique username
    public Person getPerson(String username);

    // need to list all people
    public Iterable<Person> listPeople();

    // create a new person when someone signs up
    public String createPerson(Person newPerson);

    // a person must be able to log in
    public String login(Person person);

    // option to delete account
    public HttpStatus deleteById(Long userId);

    // want to list posts by user
    public Iterable<Post> listPersonPosts(String username);

    // want to list comments by user
    public Iterable<Comment> listPersonComments(String username);

}
