package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.petproject.models.Book;
import ua.petproject.models.enums.BookCategory;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByBookCategory(BookCategory bookCategory);

    Optional<Book> findByName(String name);
}
