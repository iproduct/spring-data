package demos.springdata.springdataintro.domain;

import demos.springdata.springdataintro.dao.UserRepository;
import demos.springdata.springdataintro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }
}
