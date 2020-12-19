package mk.ukim.finki.dians.parking_application.service.implementations;

import mk.ukim.finki.dians.parking_application.model.User;
import mk.ukim.finki.dians.parking_application.model.enumeration.Role;
import mk.ukim.finki.dians.parking_application.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.dians.parking_application.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.dians.parking_application.model.exceptions.UsernameExistsException;
import mk.ukim.finki.dians.parking_application.repository.UserRepository;
import mk.ukim.finki.dians.parking_application.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname) {
        if (username == null || username.isEmpty() || password == null) {
            throw new InvalidArgumentsException();
        }
        if (!repeatPassword.equals(password)) {
            throw new PasswordsDoNotMatchException();
        }
        if (this.userRepository.findByUsername(username).isPresent())
            throw new UsernameExistsException(username);

        User user = new User(username, password, name, surname);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .orElseThrow(()-> new UsernameNotFoundException(s));
    }
}
