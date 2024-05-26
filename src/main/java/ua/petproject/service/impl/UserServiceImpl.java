package ua.petproject.service.impl;

import lombok.RequiredArgsConstructor;
import ua.petproject.models.Role;
import ua.petproject.models.User;
import ua.petproject.repository.RoleRepository;
import ua.petproject.repository.UserRepository;
import ua.petproject.service.UserService;

import java.util.Arrays;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public void saveUser(User user) {
        Role role = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }
}
