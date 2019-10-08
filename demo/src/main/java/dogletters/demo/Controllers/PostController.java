package dogletters.demo.Controllers;

import dogletters.demo.Models.Post;
import dogletters.demo.Repositories.PersonRepository;
import dogletters.demo.Repositories.PostRepository;
import dogletters.demo.Services.PersonService;
import dogletters.demo.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/list")
    public Iterable<Post> listAllPosts(){ return postService.listAllPosts(); }

    @PostMapping("/create")
    public Post createPost(@RequestBody Post post){
        return postService.createPost(post);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity deletePostById(@PathVariable Long postId) {
        // Post post = postRepository.findById(postId).get();

        // TODO
        // get id of current user
        // get id of the post author
        // compare
        // if match
        // postService.deletePostById(postId);
        // return new ResponseEntity(HttpStatus.OK);
        // else
        // return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

        // placeholder
        return null;
    }

}
