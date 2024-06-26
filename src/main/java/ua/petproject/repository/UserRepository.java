package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.petproject.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.username = :username")
    UserEntity findUserByUsernameWithRoles(@Param("username") String username);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
