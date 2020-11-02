package course.spring.dataintro.service.impl;

import course.spring.dataintro.dao.UserRepository;
import course.spring.dataintro.entity.User;
import course.spring.dataintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public void registerUser(User user) {
        userRepo.save(user);
    }
}
