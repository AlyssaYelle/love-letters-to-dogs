package dogletters.demo.Services;

import dogletters.demo.Models.Comment;

public interface CommentService {

    // need method to list all comments
    public Iterable<Comment> listAllComments();

    // need method to create a comment
    public Comment createComment(Comment comment);

    // need method to delete a comment by its unique id
    public void deleteCommentById(Long commentId);

}
