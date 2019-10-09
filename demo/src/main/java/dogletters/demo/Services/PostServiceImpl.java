package dogletters.demo.Services;

import dogletters.demo.Models.Person;
import dogletters.demo.Models.Post;
import dogletters.demo.Repositories.PersonRepository;
import dogletters.demo.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // gets the name of current authenticated user
        String currentPrincipalName = authentication.getName();

        System.out.println("creating post");
        System.out.println(currentPrincipalName);

        // creates the person object that matches logged in user
        Person person = personService.getPerson(currentPrincipalName);


        System.out.println("person object : " + person);
        System.out.println("post object: " + post);

        // now we can set the poster of the post
        post.setUser(person);

        // and add the post the the list of person's posts
        person.addPost(post);

        // and finally save to db
        return postRepository.save(post);
    }

    // TODO : test this
    @Override
    public void deletePostById(Long postId){
        Post post = postRepository.findById(postId).get();
        Person person = personRepository.findById(post.getPerson().getId()).get();
        person.getPosts().remove(post);
    }

}
