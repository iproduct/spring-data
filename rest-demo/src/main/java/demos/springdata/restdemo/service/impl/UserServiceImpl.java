package demos.springdata.restdemo.service.impl;

import demos.springdata.restdemo.dao.PostRepository;
import demos.springdata.restdemo.dao.UserRepository;
import demos.springdata.restdemo.exception.InvalidEntityException;
import demos.springdata.restdemo.model.Post;
import demos.springdata.restdemo.model.User;
import demos.springdata.restdemo.service.PostService;
import demos.springdata.restdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public Collection<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with ID " + id + " not found.")
        );
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with Username=" + username + " not found.")
        );
    }

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(Long id) {
        return null;
    }

    @Override
    public long getUsersCount() {
        return 0;
    }
}
