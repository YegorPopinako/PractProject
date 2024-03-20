package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.petproject.model.Element;
import ua.petproject.model.categories.Category;

import java.util.List;

public interface ElementRepository extends JpaRepository<Element, Long> {
    List<Element> findByCategory(Category category);
}
