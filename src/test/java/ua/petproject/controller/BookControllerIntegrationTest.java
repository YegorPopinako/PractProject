package ua.petproject.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.petproject.models.Book;
import ua.petproject.models.enums.BookCategory;
import ua.petproject.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    @SneakyThrows
    void testAddBook() {
        Book book = new Book();
        book.setName("Sample Title");
        book.setPublishingHouseName("Sample Publishing House");
        book.setAuthorName("Sample Author");
        book.setBookCategory(BookCategory.FANTASY);
        book.setPhotoUrl("sample-url.jpg");

        when(bookService.add(book)).thenReturn(book);

        mvc.perform(post("/api/books/new")
                        .param("name", "Sample Title")
                        .param("authorName", "Sample Author")
                        .param("bookCategory", "FANTASY")
                        .param("publishingHouseName", "Sample Publishing House")
                        .param("photoUrl", "sample-url.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/books"));

        verify(bookService, times(1)).add(book);
    }

    @Test
    @SneakyThrows
    void testAddInvalidBook(){

        Book book = new Book();
        book.setName("");
        book.setPublishingHouseName("Sample Publishing House");
        book.setAuthorName("Sample Author");
        book.setBookCategory(BookCategory.FANTASY);
        book.setPhotoUrl("sample-url.jpg");

        when(bookService.add(book)).thenReturn(book);

        mvc.perform(post("/api/books/new")
                        .param("name", "")
                        .param("authorName", "Sample Author")
                        .param("bookCategory", "FANTASY")
                        .param("publishingHouseName", "Sample Publishing House")
                        .param("photoUrl", "sample-url.jpg"))
                .andExpect(status().isBadRequest());

        verify(bookService, never()).add(book);
    }

    @Test
    void testGetAllBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Sample Title", BookCategory.FANTASY, "Sample Author", "Sample Author", "sample-url.jpg"));
        books.add(new Book("Sample Title 2", BookCategory.FANTASY, "Sample Author 2", "Sample Author 2", "sample-url-2.jpg"));

        when(bookService.getAllBooks()).thenReturn(books);

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", books));

        verify(bookService, times(1)).getAllBooks();
    }
}
