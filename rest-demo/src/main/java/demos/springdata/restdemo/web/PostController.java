package demos.springdata.restdemo.web;

import demos.springdata.restdemo.model.Post;
import demos.springdata.restdemo.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostService userService;

    @GetMapping
    public Collection<Post> getPosts() {
        return postService.getPosts();
    }

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post created = postService.createPost(post);
        URI location = MvcUriComponentsBuilder
                .fromMethodName(PostController.class,"addPost", post)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri();
        log.info("New POST created: {}", created);
        return ResponseEntity.created(location).body(created);
    }


}
