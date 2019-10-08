package dogletters.demo.Services;

import dogletters.demo.Models.Person;
import dogletters.demo.Models.Post;
import dogletters.demo.Repositories.PersonRepository;
import dogletters.demo.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    @Override
    public Iterable<Post> listAllPosts(){
        return postRepository.findAll();
    }

    @Override
    public Post createPost(Post post){
//        Authentication auth = authImpl.getAuthentication();
//        User user = userService.getUser(auth.getName());
//        post.setUser(user);
//        user.addPost(post);
//        return postRepository.save(post);
        System.out.println("creating post");
        // post.getPerson() returns a Person object
        // Person.getUsername returns a string username
        // personService.getPerson() returns the Person object that matches username argument
        System.out.println("post.getPerson() returns : " + post.getPerson());
        System.out.println("post.getPerson().getUsername() returns : " + post.getPerson().getUsername());
        System.out.println("personService.getPerson(post.getPerson().getUsername()) returns : " + personService.getPerson(post.getPerson().getUsername()));

        Person person = personService.getPerson(post.getPerson().getUsername());


        System.out.println("person object : " + person);
        System.out.println("post object: " + post);

        // now we can set the poster of the post
        post.setUser(person);

        // and add the post the the list of person's posts
        person.addPost(post);

        // and finally save to db
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePostById(Long postId){
        Post post = postRepository.findById(postId).get();
        Person person = personRepository.findById(post.getPerson().getId()).get();
        person.getPosts().remove(post);
    }

}
