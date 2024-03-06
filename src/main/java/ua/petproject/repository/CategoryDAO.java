package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.petproject.model.Element;

public interface CategoryDAO extends JpaRepository<Element, Long> {
}
