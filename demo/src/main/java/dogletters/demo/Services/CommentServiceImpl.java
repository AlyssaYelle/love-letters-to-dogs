package dogletters.demo.Services;

import dogletters.demo.Models.Comment;
import dogletters.demo.Models.Person;
import dogletters.demo.Repositories.CommentRepository;
import dogletters.demo.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PersonRepository personRepository;


    @Autowired
    PersonService personService;

    @Override
    public Iterable<Comment> listAllComments(){
        return commentRepository.findAll();
    }

    @Override
    public Comment createComment(Comment comment){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // gets the name of current authenticated user
        String currentPrincipalName = authentication.getName();

        System.out.println("creating comment");
        System.out.println(currentPrincipalName);

        // creates the person object that matches logged in user
        Person person = personService.getPerson(currentPrincipalName);


        System.out.println("person object : " + person);
        System.out.println("comment object: " + comment);

        // now we can set the poster of the comment
        comment.setPerson(person);

        // and add the comment to the list of person's comments
        person.addComment(comment);

        // and finally save to db
        return commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(Long commentId){
        Comment comment = commentRepository.findById(commentId).get();
        Person person = personRepository.findById(comment.getPerson().getId()).get();
        person.getComments().remove(comment);
    }

}
