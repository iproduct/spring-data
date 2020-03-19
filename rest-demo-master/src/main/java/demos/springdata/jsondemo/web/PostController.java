package demos.springdata.jsondemo.web;

import com.fasterxml.jackson.annotation.JsonView;
import demos.springdata.jsondemo.exception.InvalidEntityException;
import demos.springdata.jsondemo.model.Post;
import demos.springdata.jsondemo.model.Views;
import demos.springdata.jsondemo.service.PostService;
import demos.springdata.jsondemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @GetMapping
    @JsonView(Views.Post.class)
    public Collection<Post> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("{id}")
    @JsonView(Views.Post.class)
    public Post getPosts(@PathVariable long id) {
        return postService.getPostById(id);
    }

    @DeleteMapping("{id}")
    @JsonView(Views.Post.class)
    public Post deletePosts(@PathVariable long id) {
        return postService.deletePost(id);
    }

    @PostMapping
    @JsonView(Views.Post.class)
    public ResponseEntity<Post> addPost(@RequestBody Post post, Authentication authentication) {
//        User author = userService.getUserByUsername(authentication.getName());
//        post.setAuthor(author);
        Post created = postService.addPost(post);
        URI location = MvcUriComponentsBuilder.fromMethodName(PostController.class, "addPost", post, authentication)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
        return ResponseEntity.created(location).body(created);
//        return ResponseEntity.status(303).location(location).body(created);
    }

    @PutMapping("{id}")
    @JsonView(Views.Post.class)
    public ResponseEntity<Post> updatePost(@PathVariable long id, @RequestBody Post post) {
        if(post.getId() != id) throw new InvalidEntityException(
                String.format("Post ID=%s from path is different from Entity ID=%s", id, post.getId()));
        Post updated = postService.updatePost(post);
        log.info("Post updated: {}", updated);
        return ResponseEntity.ok(updated);
    }
}
