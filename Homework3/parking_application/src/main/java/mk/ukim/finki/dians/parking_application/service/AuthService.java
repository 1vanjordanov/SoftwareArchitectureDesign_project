package mk.ukim.finki.dians.parking_application.service;

import mk.ukim.finki.dians.parking_application.model.User;

public interface AuthService {
    User login(String username, String password);

}
