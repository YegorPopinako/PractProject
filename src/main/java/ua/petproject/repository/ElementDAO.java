package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.petproject.model.Element;

public interface ElementDAO extends JpaRepository<Element, Long> {
}
