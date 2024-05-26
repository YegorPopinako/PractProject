package ua.petproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.petproject.models.Role;
import ua.petproject.models.User;
import ua.petproject.repository.RoleRepository;
import ua.petproject.repository.UserRepository;
import ua.petproject.service.UserService;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public void saveUser(User user) {
        Role role = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
