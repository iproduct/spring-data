package demos.springdata.jsondemo.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import demos.springdata.jsondemo.gson.PostGsonDeserializer;
import demos.springdata.jsondemo.gson.PostGsonSerializer;
import demos.springdata.jsondemo.model.Post;
import demos.springdata.jsondemo.model.Views;
import demos.springdata.jsondemo.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/simple")
@Slf4j
public class SimplePostController {
    @Autowired
    private PostService postService;

    private Gson gson;

    @PostConstruct
    private void init() {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(Post.class, new PostGsonSerializer())
                .registerTypeAdapter(Post.class, new PostGsonDeserializer())
                .create();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPosts() {
        return gson.toJson(postService.getPosts());
    }

    @PostMapping
    @JsonView(Views.Post.class)
    public ResponseEntity<Post> addPost(@RequestBody String body) {
//        User author = userService.getUserByUsername(authentication.getName());
//        post.setAuthor(author);
        log.info("Body received: {}", body);
        Post post = gson.fromJson(body, Post.class);
        log.info("Post deserialized: {}", post);
        Post created = postService.addPost(post);
        URI location = MvcUriComponentsBuilder.fromMethodName(SimplePostController.class, "addPost", post)
                .pathSegment("{id}").buildAndExpand(created.getId()).toUri() ;
        return ResponseEntity.created(location).body(created);
    }
}
