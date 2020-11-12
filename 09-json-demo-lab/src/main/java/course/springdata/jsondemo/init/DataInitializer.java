package course.springdata.jsondemo.init;

import course.springdata.jsondemo.entity.Post;
import course.springdata.jsondemo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

public class DataInitializer implements CommandLineRunner {
    private static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Welcome to Spring Data", "Developing data access object with Spring Data is easy ...",
                    "https://www.publicdomainpictures.net/pictures/320000/velka/rosa-klee-blute-blume.jpg",
                    "Trayan Iliev"),
            new Post("Reactive Spring Data", "Check R2DBC for reactive JDBC API ...",
                    "https://www.publicdomainpictures.net/pictures/70000/velka/spring-grass-in-sun-light.jpg",
                    "Trayan Iliev"),
            new Post("New in Spring 5", "Webflux provides reactive and non-blocking web service implemntation ...",
                    "https://www.publicdomainpictures.net/pictures/320000/velka/blute-blumen-garten-bluhen-1577191608UTW.jpg",
                    "Trayan Iliev"),
            new Post("Beginnig REST with Spring 5", "Spring MVC and WebFlux make implemeting RESTful services really easy ...",
                    "https://www.publicdomainpictures.net/pictures/20000/velka/baby-lamb.jpg",
                    "Trayan Iliev")
    );
//    private static final List<User> SAMPLE_USERS = List.of(
//            new User("Default", "Admin", "admin", "admin", ROLE_ADMIN,
//                    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d2/Crystal_Clear_kdm_user_female.svg/500px-Crystal_Clear_kdm_user_female.svg.png"),
//            new User("Ivan", "Pertov", "ivan", "ivan", ROLE_USER,
//                    "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Crystal_Clear_app_Login_Manager.svg/500px-Crystal_Clear_app_Login_Manager.svg.png")
//    );

    @Autowired
    private PostService postService;



    @Override
    public void run(String... args) throws Exception {

//        postService.addPost()
    }
}
