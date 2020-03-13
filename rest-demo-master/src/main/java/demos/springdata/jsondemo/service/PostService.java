package demos.springdata.jsondemo.service;

import demos.springdata.jsondemo.model.Post;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface PostService {
    Collection<Post> getPosts();
    Post addPost(@Valid Post post);
    Post updatePost(Post post);
    Post getPostById(long id);
    Post deletePost(long id);
    List<Post> createPostsBatch(List<Post> posts);
    long getPostsCount();
}
