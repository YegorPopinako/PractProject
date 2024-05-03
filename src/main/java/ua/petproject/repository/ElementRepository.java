package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.petproject.models.Element;
import ua.petproject.models.enums.ElementCategory;

import java.util.List;

public interface ElementRepository extends JpaRepository<Element, Long> {
    List<Element> findByElementCategory(ElementCategory elementCategory);
}
