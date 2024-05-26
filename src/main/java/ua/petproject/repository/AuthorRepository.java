package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.petproject.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);
}
