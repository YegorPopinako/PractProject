package ua.petproject.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import ua.petproject.model.Element;
import ua.petproject.model.categories.Category;
import ua.petproject.service.ElementService;

import java.util.List;

@WebMvcTest({ ElementController.class })
class ElementControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ElementService elementService;

    @Test
    @SneakyThrows
    void testAdd_shouldReturnStatusCodeIsCreated() {
        var element = new Element();
        element.setName("username");
        element.setCategory(Category.FIRST);

        var elementAsString = mapper.writeValueAsString(element);

        when(elementService.add(element)).thenReturn(element);

        mvc.perform(post("/api/elements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(elementAsString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("username"))
                .andExpect(jsonPath("$.category").value(Category.FIRST.toString()));
    }

    @Test
    @SneakyThrows
    void testAdd_WhenEnumParameterIsInvalidShouldReturnBadRequest() {

        var elementAsString = """
                {
                    "name": "username",
                    "category": INVALID
                }
                """;

        mvc.perform(post("/api/elements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(elementAsString))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(elementService, times(0)).add(any());
    }

    @Test
    @SneakyThrows
    void testAdd_shouldReturnStatusCodeBadRequest() {

        mvc.perform(post("/api/elements")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @SneakyThrows
    void testGet_shouldReturnStatusCodeOk() {
        long id = 1L;
        var element = new Element();
        element.setId(id);
        element.setName("username");
        element.setCategory(Category.FIRST);

        when(elementService.get(id)).thenReturn(element);

        mvc.perform(get("/api/elements/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("username"))
                .andExpect(jsonPath("$.category").value(Category.FIRST.toString()));
    }

    @Test
    @SneakyThrows
    void testGetElement_ElementNotFound() {
        long id = 1L;

        doThrow(new EntityNotFoundException("Entity not found")).when(elementService).get(id);

        mvc.perform(get("/api/elements/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testGetAll_shouldReturnStatusCodeOk() {
        var element1 = new Element();
        element1.setName("username1");
        element1.setCategory(Category.FIRST);

        var element2 = new Element();
        element2.setName("username2");
        element2.setCategory(Category.FIRST);

        when(elementService.getAll(Category.FIRST)).thenReturn(List.of(element1, element2));

        mvc.perform(get("/api/elements")
                        .param("category", Category.FIRST.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("username1"))
                .andExpect(jsonPath("$[0].category").value(Category.FIRST.toString()))
                .andExpect(jsonPath("$[1].name").value("username2"))
                .andExpect(jsonPath("$[1].category").value(Category.FIRST.toString()));
    }

    @Test
    @SneakyThrows
    void testUpdate_shouldReturnStatusCodeOk() {
        long id = 1L;

        var updatedElement = new Element();
        updatedElement.setId(id);
        updatedElement.setName("username2");
        updatedElement.setCategory(Category.SECOND);

        when(elementService.update(id, updatedElement)).thenReturn(updatedElement);

        var elementAsString = mapper.writeValueAsString(updatedElement);

        mvc.perform(put("/api/elements/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(elementAsString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(updatedElement.getName()))
                .andExpect(jsonPath("$.category").value(updatedElement.getCategory().toString()));
    }

    @Test
    @SneakyThrows
    void testUpdate_shouldReturnStatusCodeNotFound() {
        long id = 1L;
        var updatedElement = new Element();
        updatedElement.setId(id);
        updatedElement.setName("username2");
        updatedElement.setCategory(Category.SECOND);

        when(elementService.update(id, updatedElement)).thenThrow(EntityNotFoundException.class);

        mvc.perform(put("/api/elements/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedElement)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Entity not found"));
    }

    @Test
    @SneakyThrows
    void testDelete_shouldReturnStatusCodeNoContent() {
        long id = 1L;

        mvc.perform(delete("/api/elements/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void testDelete_shouldReturnStatusCodeNotFound() {
        long id = 1L;

        doThrow(EntityNotFoundException.class).when(elementService).delete(id);

        mvc.perform(delete("/api/elements/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testAdd_ElementWithNullCategoryShouldReturnBadRequest() {
        var element = new Element();
        element.setName("Test Element");
        element.setCategory(null);

        var elementAsString = mapper.writeValueAsString(element);

        mvc.perform(post("/api/elements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(elementAsString))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Method argument not valid"));

        verify(elementService, times(0)).add(element);
    }

    @Test
    @SneakyThrows
    void testGetAll_InvalidCategoryParameterShouldReturnBadRequest() {
        String invalidCategory = "INVALID";

        mvc.perform(get("/api/elements")
                        .param("category", invalidCategory))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(elementService, times(0)).getAll(any());
    }

    @Test
    @SneakyThrows
    void testUpdate_InvalidElementShouldReturnBadRequest() {
        Long id = 1L;
        Element invalidElement = new Element();
        invalidElement.setId(id);
        invalidElement.setName("Test Element");
        invalidElement.setCategory(null);

        String invalidElementJson = mapper.writeValueAsString(invalidElement);

        mvc.perform(put("/api/elements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidElementJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Method argument not valid"));

        verify(elementService, times(0)).update(id, invalidElement);
    }

    @Test
    @SneakyThrows
    void testUpdate_whenElementFieldNotValid_shouldReturnBadRequest() {
        Long id = 1L;
        Element invalidElement = new Element();
        invalidElement.setId(id);
        invalidElement.setName("Test Element");
        invalidElement.setCategory(null);

        String invalidElementJson = mapper.writeValueAsString(invalidElement);

        mvc.perform(put("/api/elements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidElementJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Method argument not valid"));

        verify(elementService, times(0)).update(id, invalidElement);
    }
}