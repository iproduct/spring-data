package course.springdata.jsondemo.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import course.springdata.jsondemo.entity.User;
import course.springdata.jsondemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/gson/users")
@Slf4j
public class GsonUserController {
    @Autowired
    private UserService userService;

    private Gson gson = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

    @GetMapping(produces = "application/json")
    public String getUsers() {
        return gson.toJson(userService.getAllUsers());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public String getUsers(@PathVariable("id") Long id) {
        return gson.toJson(userService.getUserById(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<String> addUser(@RequestBody String body) {
        log.info("Body received: {}", body);
        User user = gson.fromJson(body, User.class);
        log.info("User deserialized: {}", user);
        User created = userService.addUser(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .pathSegment("{id}")
                    .buildAndExpand(created.getId().toString())
                    .toUri()
        ).body(gson.toJson(created));
    }
}
