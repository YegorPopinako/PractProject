package ua.petproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.petproject.models.Book;
import ua.petproject.models.UserEntity;
import ua.petproject.models.enums.BookCategory;
import ua.petproject.service.BookService;
import ua.petproject.service.UserService;
import ua.petproject.utils.SecurityUtil;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    private final UserService userService;

    @GetMapping("/new")
    public String createBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "books-create";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("book") @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "books-create";
        }
        try {
            bookService.add(book);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "books-create";
        }
        return "redirect:/api/books";
    }

    @GetMapping
    public String getAllBooks(Model model) {
        UserEntity user = new UserEntity();
        List<Book> books = bookService.getAllBooks();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model) {
        UserEntity user = new UserEntity();
        Book book = bookService.get(id);
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("book", book);
        return "books-details";
    }

    @GetMapping("/{id}/edit")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        return "books-edit";
    }

    @PatchMapping("/{id}/edit")
    public String partialUpdateBook(@PathVariable("id") Long id, @RequestParam Map<String, String> updates) {
        bookService.partialUpdate(id, updates);
        return "redirect:/api/books";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
        return "redirect:/api/books";
    }
}
