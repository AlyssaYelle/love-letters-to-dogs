package dogletters.demo.Controllers;

import dogletters.demo.Models.Comment;
import dogletters.demo.Repositories.CommentRepository;
import dogletters.demo.Repositories.PersonRepository;
import dogletters.demo.Services.CommentService;
import dogletters.demo.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/list")
    public Iterable<Comment> listAllComments(){
        return commentService.listAllComments();
    }

    @PostMapping("/create")
    public Comment createComment(@RequestBody Comment comment){

        return commentService.createComment(comment);
    }

    // user should be able to delete their posts
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity deleteCommentById(@PathVariable Long commentId) {
        // Post post = postRepository.findById(postId).get();

        // TODO
        // get id of current user
        // get id of the comment author
        // compare
        // if match
        // commentService.deleteCommentById(commentId);
        // return new ResponseEntity(HttpStatus.OK);
        // else
        // return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

        // placeholder
        return null;
    }
}
