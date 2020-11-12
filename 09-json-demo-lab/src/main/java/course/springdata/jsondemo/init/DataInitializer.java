package course.springdata.jsondemo.init;

import course.springdata.jsondemo.entity.Post;
import course.springdata.jsondemo.entity.User;
import course.springdata.jsondemo.service.PostService;
import course.springdata.jsondemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static course.springdata.jsondemo.entity.Role.ADMIN;
import static course.springdata.jsondemo.entity.Role.USER;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Welcome to Spring Data", "Developing data access object with Spring Data is easy ...",
                    "https://www.publicdomainpictures.net/pictures/320000/velka/rosa-klee-blute-blume.jpg",
                    1L),
            new Post("Reactive Spring Data", "Check R2DBC for reactive JDBC API ...",
                    "https://www.publicdomainpictures.net/pictures/70000/velka/spring-grass-in-sun-light.jpg",
                    1L),
            new Post("New in Spring 5", "Webflux provides reactive and non-blocking web service implemntation ...",
                    "https://www.publicdomainpictures.net/pictures/320000/velka/blute-blumen-garten-bluhen-1577191608UTW.jpg",
                    1L),
            new Post("Beginnig REST with Spring 5", "Spring MVC and WebFlux make implemeting RESTful services really easy ...",
                    "https://www.publicdomainpictures.net/pictures/20000/velka/baby-lamb.jpg",
                    1L)
    );
    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "admin", "admin", ADMIN,
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d2/Crystal_Clear_kdm_user_female.svg/500px-Crystal_Clear_kdm_user_female.svg.png"),
            new User("Ivan", "Pertov", "ivan", "ivan123", USER,
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Crystal_Clear_app_Login_Manager.svg/500px-Crystal_Clear_app_Login_Manager.svg.png")
    );

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;



    @Override
    public void run(String... args) throws Exception {
        SAMPLE_USERS.forEach(userService::addUser);
        SAMPLE_POSTS.forEach(postService::addPost);
    }
}
