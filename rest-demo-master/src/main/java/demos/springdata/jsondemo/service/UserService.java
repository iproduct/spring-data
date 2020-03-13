package demos.springdata.jsondemo.service;

import demos.springdata.jsondemo.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    List<User> getUsers();
    User createUser(@Valid User user);
    User updateUser(User user);
    User getUserById(long id);
    User getUserByUsername(String username);
    User deleteUser(long id);
    List<User> createUsersBatch(List<User> users);
    long getUsersCount();
}
