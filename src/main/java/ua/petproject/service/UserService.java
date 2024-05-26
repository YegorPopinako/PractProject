package ua.petproject.service;

import ua.petproject.models.User;

public interface UserService {
    void saveUser(User user);

    User findByEmail(String email);

    User findByUsername(String username);
}
