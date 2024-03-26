package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.petproject.models.Book;
import ua.petproject.models.categories.BookCategory;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByBookCategory(BookCategory bookCategory);
}
