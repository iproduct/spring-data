package course.springdata.jsondemo.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import course.springdata.jsondemo.dao.PostRepository;
import course.springdata.jsondemo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/posts")
public class GsonPostController {
    @Autowired
    private PostService postService;

    private Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

    @GetMapping
    public String getPosts() {
        return gson.toJson(postService.getAllPosts());
    }
}
