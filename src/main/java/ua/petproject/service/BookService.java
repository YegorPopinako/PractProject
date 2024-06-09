package ua.petproject.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import ua.petproject.models.Author;
import ua.petproject.models.Book;
import ua.petproject.models.PublishingHouse;
import ua.petproject.models.enums.BookCategory;

import java.util.List;
import java.util.Map;

public interface BookService {

    /**
     * Adds a new book to the Database.
     *
     * @param book The book to be added.
     * @return The added book.
     */
    Book add(Book book);

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The retrieved book.
     * @throws EntityNotFoundException If the book with the specified ID is not found.
     */
    Book get(Long id) throws EntityNotFoundException;

    /**
     * Retrieves all books by category.
     *
     * @param bookCategory The category by which to filter the books.
     * @return The list of retrieved books.
     */
    List<Book> getAll(BookCategory bookCategory);

    /**
     * Updates an existing book.
     *
     * @param id   The ID of the book to be updated.
     * @param updates The new data for the book.
     * @return Updated book.
     */
    Book partialUpdate(Long id, Map<String, String> updates);

    /**
     * Retrieves all books from the database.
     *
     * @return The list of retrieved books.
     */
    List<Book> getAllBooks();

    /**
     * Deletes a book from the system by its ID.
     *
     * @param id The ID of the book to be deleted.
     * @throws EntityNotFoundException If the book with the specified ID is not found.
     */
    void delete(Long id) throws EntityNotFoundException;

}
