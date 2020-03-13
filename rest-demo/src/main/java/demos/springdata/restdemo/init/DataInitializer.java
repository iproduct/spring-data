package demos.springdata.restdemo.init;

import demos.springdata.restdemo.model.Post;
import demos.springdata.restdemo.model.User;
import demos.springdata.restdemo.service.PostService;
import demos.springdata.restdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    private static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Welcome to Spring Data", "Developing data access object with Spring Data is easy ..."),
            new Post("Reactive Spring Data", "Check R2DBC for reactive JDBC API ..."),
            new Post("New in Spring 5", "Webflux provides reactive and non-blocking web service implemntation ...")
    );
    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "admin"),
            new User("Ivan", "Pertov", "ivan", "ivan")
    );
    @Override
    public void run(String... args) throws Exception {
        SAMPLE_USERS.forEach(user -> userService.addUser(user));
        log.info("Created Users: {}", userService.getUsers());
        SAMPLE_POSTS.forEach(post -> {
            post.setAuthor(userService.getUserById(1L));
            postService.addPost(post);
        });
        log.info("Created Posts: {}", postService.getPosts());
    }
}
