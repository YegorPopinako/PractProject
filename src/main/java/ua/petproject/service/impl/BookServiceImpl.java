package ua.petproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.petproject.models.Author;
import ua.petproject.models.Book;
import ua.petproject.models.PublishingHouse;
import ua.petproject.models.categories.BookCategory;
import ua.petproject.repository.AuthorRepository;
import ua.petproject.repository.BookRepository;
import ua.petproject.repository.PublishingHouseRepository;
import ua.petproject.service.BookService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublishingHouseRepository publishingHouseRepository;

    @Override
    @Transactional
    public Book add(Book book) {
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
    @Transactional
    public Book update(Long id, Book book) {
        try {
            Book existingBook = get(id);
            if (book.getName() != null) {
                existingBook.setName(book.getName());
            }
            if (book.getPhotoUrl() != null) {
                existingBook.setPhotoUrl(book.getPhotoUrl());
            }
            if (book.getBookCategory() != null) {
                existingBook.setBookCategory(book.getBookCategory());
            }
            if (book.getAuthor() != null) {
                existingBook.setAuthor(book.getAuthor());
            }
            if (book.getPublishingHouse() != null) {
                existingBook.setPublishingHouse(book.getPublishingHouse());
            }
            return existingBook;
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Book with ID " + id + " not found.");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    public Author findOrCreateAuthor(String authorName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            author = authorRepository.save(new Author(authorName));
        }
        return author;
    }

    @Transactional
    public PublishingHouse findOrCreatePublishingHouse(String publishingHouseName) {
        PublishingHouse publishingHouse = publishingHouseRepository.findByName(publishingHouseName);
        if (publishingHouse == null) {
            publishingHouse = publishingHouseRepository.save(new PublishingHouse(publishingHouseName));
        }
        return publishingHouse;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
