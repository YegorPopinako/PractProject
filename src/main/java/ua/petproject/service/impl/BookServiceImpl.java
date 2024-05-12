package ua.petproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.petproject.models.Author;
import ua.petproject.models.Book;
import ua.petproject.models.PublishingHouse;
import ua.petproject.models.enums.BookCategory;
import ua.petproject.repository.BookRepository;
import ua.petproject.service.AuthorService;
import ua.petproject.service.BookService;
import ua.petproject.service.PublishingHouseService;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final PublishingHouseService publishingHouseService;

    @Override
    @Transactional
    public Book add(Book book) {
        Author author = authorService.findOrCreateAuthor(book.getAuthorName());
        PublishingHouse publishingHouse = publishingHouseService.findOrCreatePublishingHouse(book.getPublishingHouseName());
        book.setAuthor(author);
        book.setPublishingHouse(publishingHouse);
        return bookRepository.save(book);
    }

    @Override
    public Book get(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    @Override
    public List<Book> getAll(BookCategory bookCategory) {
        return bookRepository.findByBookCategory(bookCategory);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Book partialUpdate(Long id, Map<String, String> updates) {
        try {
            Book existingBook = get(id);
            updates.forEach((key, value) -> {
                switch (key) {
                    case "name":
                        existingBook.setName(value);
                        break;
                    case "bookCategory":
                        existingBook.setBookCategory(BookCategory.valueOf(value));
                        break;
                    case "authorName":
                        existingBook.setAuthorName(value);
                        break;
                    case "publishingHouseName":
                        existingBook.setPublishingHouseName(value);
                        break;
                    case "photoUrl":
                        existingBook.setPhotoUrl(value);
                        break;
                }
            });
            return existingBook;
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Book with ID " + id + " not found.");
        }
    }
}
