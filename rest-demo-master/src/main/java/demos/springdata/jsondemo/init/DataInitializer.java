package demos.springdata.jsondemo.init;

import demos.springdata.jsondemo.model.Post;
import demos.springdata.jsondemo.model.User;
import demos.springdata.jsondemo.service.PostService;
import demos.springdata.jsondemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private static final List<Post> mockPosts = Arrays.asList(
            new Post("Welcome to Spring 5", "Spring 5 is great beacuse ..."),
            new Post("Dependency Injection", "Should I use DI or lookup ..."),
            new Post("New in Spring 5", "There are several ways to configure Spring beans.")
    );

    @Autowired
    PostService articleService;

    @Autowired
    UserService userService;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Users init
        long usersCount = userService.getUsersCount();
        log.info("Users count: {}", usersCount);
//
        if (usersCount == 0) {
            List<User> defaultUsers = Arrays.asList(
                    new User("admin", "admin", "DEFAULT", "ADMIN", "ROLE_ADMIN"),
                    new User("ivan", "ivan", "Ivan", "Petrov", "ROLE_USER")
            );

            defaultUsers.stream().forEach(user -> userService.createUser(user));

//            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(users);
//            jdbcTemplate.batchUpdate(UserRepositoryImpl.INSERT_SQL, batch);
        }

        log.info("Querying for user records:");
        List<User> users = userService.getUsers();
        users.stream().forEach(user -> log.info("{}", user.getUsername()));


        // Posts init
        long countBefore = articleService.getPostsCount();
        if (users.size() > 0) {
            User defaultUser = users.get(0);
            if (countBefore == 0 && users.size() > 0) {
                mockPosts.stream().forEach(article -> article.setAuthor(defaultUser));
                try {
                    List<Post> created = articleService.createPostsBatch(mockPosts);
                    log.info(">>> Posts batch created: {}", created);
                } catch (ConstraintViolationException ex) {
                    log.error(">>> Constraint violation inserting articles: {} - {}", mockPosts, ex.getMessage());
                }
                long countAfter = articleService.getPostsCount();
                log.info(">>> Total count of articles created: {}", countAfter - countBefore);
            }
        }
    }

}
