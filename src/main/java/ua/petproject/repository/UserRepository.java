package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.petproject.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

}
