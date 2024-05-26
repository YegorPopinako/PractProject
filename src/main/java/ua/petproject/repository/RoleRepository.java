package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.petproject.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
