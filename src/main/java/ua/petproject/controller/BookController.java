package ua.petproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.petproject.models.Author;
import ua.petproject.models.Book;
import ua.petproject.models.PublishingHouse;
import ua.petproject.models.categories.BookCategory;
import ua.petproject.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("/new")
    public String createBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "books-create";
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@ModelAttribute("book") @Valid Book book) {
        String authorName = book.getAuthorName();
        String publishingHouseName = book.getPublishingHouseName();
        Author author = bookService.findOrCreateAuthor(authorName);
        PublishingHouse publishingHouse = bookService.findOrCreatePublishingHouse(publishingHouseName);
        book.setAuthor(author);
        book.setPublishingHouse(publishingHouse);
        bookService.add(book);
        return "redirect:/api/books";
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        return "books-details";
    }

    @GetMapping("/fantasy")
    public String getAll(Model model) {
        List<Book> books = bookService.getAll(BookCategory.FANTASY);
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/{id}/edit")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        return "books-edit";
    }

    @PostMapping("/{id}/edit")
    public String editBook(@PathVariable("id") Long id, @ModelAttribute("book") @Valid Book book) {
        bookService.update(id, book);
        return "redirect:/api/books";
    }

    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
        return "redirect:/api/books";
    }
}
