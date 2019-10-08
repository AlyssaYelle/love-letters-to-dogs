package dogletters.demo.Services;

import dogletters.demo.Models.Post;

public interface PostService {

    public Iterable<Post> listAllPosts();

    public Post createPost(Post post);

    public void deletePostById(Long postId);
}
