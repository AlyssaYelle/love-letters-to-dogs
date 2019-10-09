package dogletters.demo.Controllers;

import dogletters.demo.Models.Post;
import dogletters.demo.Repositories.PersonRepository;
import dogletters.demo.Repositories.PostRepository;
import dogletters.demo.Services.PersonService;
import dogletters.demo.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Iterable<Post> listAllPosts(){
        return postService.listAllPosts();
    }

    // a logged in user should be able to create a post
//    {
//        "title" : "a title",
//        "content" : "some content"
//    }
    @PostMapping("/create")
    public Post createPost(@RequestBody Post post){
        return postService.createPost(post);
    }
    // response looks like:
//    {
//        "id": 4,
//            "title": "Garry",
//            "content": "Garry is 80lbs of sweetness topped with a BIG precious head."
//    }

    // user should be able to delete their posts
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
