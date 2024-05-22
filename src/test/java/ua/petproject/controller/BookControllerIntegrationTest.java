package ua.petproject.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.petproject.models.Book;
import ua.petproject.models.enums.BookCategory;
import ua.petproject.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @SneakyThrows
    @ParameterizedTest(name = "{0} {1} {2} {3} {4}")
    @MethodSource("sourceAddInvalidBook")
    void testAddInvalidBook(String name, String authorName, String bookCategory, String publishingHouseName, String photoUrl) {

        Book book = new Book();
        book.setName(name);
        book.setPublishingHouseName(publishingHouseName);
        book.setAuthorName(authorName);
        book.setBookCategory(BookCategory.valueOf(bookCategory));
        book.setPhotoUrl(photoUrl);

        doThrow(new IllegalArgumentException()).when(bookService).add(book);

        mvc.perform(post("/api/books/new")
                        .param("name", name)
                        .param("authorName", authorName)
                        .param("bookCategory", bookCategory)
                        .param("publishingHouseName", publishingHouseName)
                        .param("photoUrl", photoUrl))
                .andExpect(status().isBadRequest());

        verify(bookService,never()).add(book);
    }

    private static Stream<Arguments> sourceAddInvalidBook() {
        return Stream.of(
                Arguments.of("", "authorName", "FANTASY", "publishingHouseName", "photoUrl"),
                Arguments.of("name", "", "FANTASY", "publishingHouseName", "photoUrl"),
                Arguments.of("name", "authorName", "FANTASY", "", "photoUrl"),
                Arguments.of("name", "authorName", "FANTASY", "publishingHouseName", "")
        );
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
