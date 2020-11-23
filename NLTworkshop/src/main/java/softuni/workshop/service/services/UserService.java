package softuni.workshop.service.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.workshop.service.models.UserServiceModel;
import softuni.workshop.web.models.UserRegisterModel;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserRegisterModel userRegisterModel);
}
