package ua.petproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.petproject.models.UserEntity;
import ua.petproject.models.enums.Roles;
import ua.petproject.repository.UserRepository;
import ua.petproject.service.UserService;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveUser(UserEntity userEntity) {
        UserEntity existingUser = userRepository.findByUsername(userEntity.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("User already exists!");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Roles.USER);
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

