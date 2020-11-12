package course.springdata.jsondemo.service;

import course.springdata.jsondemo.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User addUser(User user);
    User updateUser(User user);
    User deleteUser(Long id);
    long getUsersCount();
}
