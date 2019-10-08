package dogletters.demo.Services;

import dogletters.demo.Config.JwtUtil;
import dogletters.demo.Models.Comment;
import dogletters.demo.Models.Person;
import dogletters.demo.Models.PersonRole;
import dogletters.demo.Models.Post;
import dogletters.demo.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    // tells Spring to look for and inject PersonRepository when PersonServiceImpl is created
    @Autowired
    PersonRepository personRepository;

    // tells Spring to look for and inject PersonRoleService
    @Autowired
    PersonRoleService personRoleService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    @Qualifier("encoder")
    PasswordEncoder bCryptPasswordEncoder;

    // find person by their username
    @Override
    public Person getPerson(String username) {
        return personRepository.findByUsername(username);
    }

    // list all people in person db
    @Override
    public Iterable<Person> listPeople() {
        return personRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = getPerson(username);

        if(person==null)
            throw new UsernameNotFoundException("User null");

        return new org.springframework.security.core.userdetails.User(person.getUsername(), bCryptPasswordEncoder.encode(person.getPassword()),
                true, true, true, true, getGrantedAuthorities(person));
    }

    // i'm not sure what this is for??
    // do we use this?
    private List<GrantedAuthority> getGrantedAuthorities(Person person){
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(person.getPersonRole().getName()));

        return authorities;
    }

    // create a new person for sign up
    // string because it returns a token, not a person
    @Override
    public String createPerson(Person newPerson) {
        System.out.println("creating user");

        // create a person role
        // to do this:
        // newPerson.getPersonRole() returns a role object
        // call getName() method on the role object, which returns a string
        // personRoleService.getRole(String roleName) returns a personRole object
        PersonRole personRole = personRoleService.getRole(newPerson.getPersonRole().getName());

        // now set the role of the Person object we are creating
        newPerson.setPersonRole(personRole);

        // set the password to the encrypted version of their inputted password
        newPerson.setPassword(bCryptPasswordEncoder.encode(newPerson.getPassword()));

        // generate token and return it
        if(personRepository.save(newPerson) != null){
            UserDetails userDetails = loadUserByUsername(newPerson.getUsername());
            return jwtUtil.generateToken(userDetails);
        }
        return null;

    }

    // log in existing person
    // also string because it returns a token
    // auth : no auth
    @Override
    public String login(Person person) {
        // grab a person object from db using findByUserName method
        Person newPerson = personRepository.findByUsername(person.getUsername());

        // return null if user is not found
        // otherwise check that password matches, load user details, and return a token
        if(newPerson != null && bCryptPasswordEncoder.matches(person.getPassword(), newPerson.getPassword())){
            UserDetails userDetails = loadUserByUsername(newPerson.getUsername());
            return jwtUtil.generateToken(userDetails);
        }
        return null;
    }

    // log out person
    @Override
    public HttpStatus deleteById(Long userId) {
        personRepository.deleteById(userId);
        return HttpStatus.OK;
    }

    @Override
    public Iterable<Comment> listPersonComments(String username){
        Person person = getPerson(username);
        return person.getComments();
    }

    @Override
    public Iterable<Post> listPersonPosts(String username) {
        Person person = getPerson(username);
        return person.getPosts();
    }

}
