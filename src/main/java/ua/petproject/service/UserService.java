package ua.petproject.service;

import ua.petproject.models.UserEntity;

public interface UserService {
    void saveUser(UserEntity userEntity);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
