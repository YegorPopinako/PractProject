package ua.petproject.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.petproject.config.SecurityConfig;
import ua.petproject.models.Book;
import ua.petproject.models.UserEntity;
import ua.petproject.models.enums.BookCategory;
import ua.petproject.service.BookService;
import ua.petproject.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
    class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "bookServiceImpl")
    private BookService bookService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "testuser", authorities = "USER")
    @SneakyThrows
    void testGetExistingBookById() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");

        Long bookId = 1L;

        Book book = new Book();
        book.setId(bookId);
        book.setName("Test Book");
        book.setAuthorName("Test Author");
        book.setBookCategory(BookCategory.HORROR);
        book.setPublishingHouseName("Test Publishing House");
        book.setPhotoUrl("test_photo_url");
        book.setUser(user);

        when(bookService.get(bookId)).thenReturn(book);
        when(userService.findByUsername("testuser")).thenReturn(user);

        mvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(view().name("books-details"))
                .andExpect(model().attributeExists("book", "user"));

        verify(bookService).get(bookId);
        verify(userService).findByUsername("testuser");
    }

    @Test
    @WithMockUser(authorities = "USER")
    @SneakyThrows
    void testAddBook() {
        Book book = new Book();
        book.setName("Sample Title");
        book.setPublishingHouseName("Sample Publishing House");
        book.setAuthorName("Sample Author");
        book.setBookCategory(BookCategory.FANTASY);
        book.setPhotoUrl("sample-url.jpg");

        when(bookService.add(book)).thenReturn(book);

        mvc.perform(post("/books/new")
                        .param("name", "Sample Title")
                        .param("authorName", "Sample Author")
                        .param("bookCategory", "FANTASY")
                        .param("publishingHouseName", "Sample Publishing House")
                        .param("photoUrl", "sample-url.jpg")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookService).add(book);
    }

    @ParameterizedTest(name = "{0} {1} {2} {3} {4}")
    @MethodSource("sourceAddInvalidBook")
    @WithMockUser(authorities = "USER")
    @SneakyThrows
    void testAddInvalidBook(String name, String authorName, String bookCategory, String publishingHouseName, String photoUrl) {

        Book book = new Book();
        book.setName(name);
        book.setPublishingHouseName(publishingHouseName);
        book.setAuthorName(authorName);
        book.setBookCategory(BookCategory.valueOf(bookCategory));
        book.setPhotoUrl(photoUrl);

        doThrow(new IllegalArgumentException()).when(bookService).add(book);

        mvc.perform(post("/books/new")
                        .param("name", name)
                        .param("authorName", authorName)
                        .param("bookCategory", bookCategory)
                        .param("publishingHouseName", publishingHouseName)
                        .param("photoUrl", photoUrl)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("books-create"))
                .andExpect(model().attributeExists("book"));

        verify(bookService, never()).add(book);
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
    @WithMockUser(username = "testuser", authorities = "USER")
    @SneakyThrows
    void testGetAllBooks() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");

        List<Book> books = new ArrayList<>();
        books.add(new Book("Sample Title", BookCategory.FANTASY, "Sample Author", "Sample Publishing House", "sample-url.jpg"));
        books.add(new Book("Sample Title 2", BookCategory.FANTASY, "Sample Author 2", "Sample Publishing House 2", "sample-url-2.jpg"));
        books.add(new Book("Sample Title 3", BookCategory.HORROR, "Sample Author 3", "Sample Publishing House 3", "sample-url-3.jpg"));
        books.add(new Book("Sample Title 4", BookCategory.SCI_FI, "Sample Author 4", "Sample Publishing House 4", "sample-url-4.jpg"));

        for (Book book : books) {
            book.setUser(user);
        }

        when(bookService.getAllBooks()).thenReturn(books);
        when(userService.findByUsername("testuser")).thenReturn(user);

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", books))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));

        verify(bookService).getAllBooks();
        verify(userService).findByUsername("testuser");
    }

    @Test
    @WithMockUser(username = "testuser", authorities = "USER")
    @SneakyThrows
    void testGetEmptyBooks() {
        List<Book> books = new ArrayList<>();
        when(bookService.getAllBooks()).thenReturn(books);

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", books));

        verify(bookService, times(1)).getAllBooks();
    }

    @ParameterizedTest(name = "when book with id = {0} doesn't exist")
    @MethodSource("sourceInvalidBookId")
    @WithMockUser(username = "testuser", authorities = "USER")
    @SneakyThrows
    void testGetNonExistingBookById(Long id){

        when(bookService.get(id)).thenThrow(EntityNotFoundException.class);

        mvc.perform(get("/books/" + id))
                .andExpect(status().isNotFound());

        verify(bookService).get(id);
    }

    private static Stream<Arguments> sourceInvalidBookId() {
        return Stream.of(
                Arguments.of(2L),
                Arguments.of(3L),
                Arguments.of(14L)
        );
    }

    @Test
    @WithMockUser(authorities = "USER")
    void testEditBook() throws Exception {
        Book book = new Book("Sample Title",
                BookCategory.FANTASY,
                "Sample Author",
                "Sample Author",
                "sample-url.jpg");

        when(bookService.isUserCreator(1L)).thenReturn(true);
        when(bookService.partialUpdate(anyLong(), argThat(map ->
                "Sample Title2".equals(map.get("name")) && map.size() == 2))).thenReturn(book);

        mvc.perform(patch("/books/1/edit")
                        .with(csrf())
                        .param("name", "Sample Title2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookService).partialUpdate(eq(1L), argThat(map ->
                "Sample Title2".equals(map.get("name")) && map.size() == 2));
    }

    @ParameterizedTest(name = "when book with id = {0} doesn't exist")
    @MethodSource("sourceInvalidBookId")
    @WithMockUser(authorities = "USER")
    @SneakyThrows
    void testEditNonExistingBook(Long id) {

        when(bookService.isUserCreator(id)).thenReturn(true);
        when(bookService.partialUpdate(anyLong(), argThat(map -> "Sample Title2".equals(map.get("name")) && map.size() == 2)))
                .thenThrow(new EntityNotFoundException());

        mvc.perform(patch("/books/" + id + "/edit")
                        .with(csrf())
                        .param("name", "Sample Title2"))
                .andExpect(status().isNotFound());

        verify(bookService).partialUpdate(eq(id), argThat(map ->
                "Sample Title2".equals(map.get("name")) && map.size() == 2));
    }

    @ParameterizedTest(name = "when book with id = {0} doesn't exist")
    @MethodSource("sourceInvalidBookId")
    @WithMockUser(authorities = "USER")
    @SneakyThrows
    void testDeleteExistingBook(Long id){

        doNothing().when(bookService).delete(id);
        when(bookService.isUserCreator(id)).thenReturn(true);

        mvc.perform(delete("/books/" + id + "/delete")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookService).delete(id);
    }

    @ParameterizedTest(name = "when book with id = {0} doesn't exist")
    @MethodSource("sourceInvalidBookId")
    @WithMockUser(authorities = "USER")
    @SneakyThrows
    void testDeleteNonExistingBook(Long id){

        doThrow(new EntityNotFoundException()).when(bookService).delete(id);
        when(bookService.isUserCreator(id)).thenReturn(true);

        mvc.perform(delete("/books/" + id + "/delete")
                        .with(csrf()))
                .andExpect(status().isNotFound());

        verify(bookService).delete(id);
    }
}
