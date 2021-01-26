package mk.ukim.finki.dians.parking_application.service;

import mk.ukim.finki.dians.parking_application.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname);

}