package dogletters.demo.Services;

import dogletters.demo.Models.Comment;
import dogletters.demo.Models.Person;
import dogletters.demo.Repositories.CommentRepository;
import dogletters.demo.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        /* TODO
        retrieve username from token
        comment.setPerson(person);
        person.addComment(comment);

        return commentRepository.save(comment);
         */
        return null;
    }

    @Override
    public void deleteCommentById(Long commentId){
        Comment comment = commentRepository.findById(commentId).get();
        Person person = personRepository.findById(comment.getPerson().getId()).get();
        person.getComments().remove(comment);
    }

}
