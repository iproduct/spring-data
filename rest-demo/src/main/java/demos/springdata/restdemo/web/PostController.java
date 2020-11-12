package demos.springdata.restdemo.web;

import com.fasterxml.jackson.annotation.JsonView;
import demos.springdata.restdemo.model.Post;
import demos.springdata.restdemo.model.Views;
import demos.springdata.restdemo.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:4000", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostService userService;

    @GetMapping
    @JsonView(Views.Post.class)
    public Collection<Post> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Post.class)
    public Post getPosts(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    @JsonView(Views.Post.class)
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post created = postService.createPost(post);
        URI location = MvcUriComponentsBuilder
                .fromMethodName(PostController.class,"addPost", post)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri();
        log.info("New POST created: {}", created);
        return ResponseEntity.created(location).body(created);
    }


}
