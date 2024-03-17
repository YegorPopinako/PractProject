package ua.petproject.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
public class ElementControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ElementService elementService;

    @Test
    @SneakyThrows
    void testAdd_shouldReturnStatusCodeOk() {
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
    void testAdd_shouldReturnStatusCodeBadRequest() {
        Element element = null;

        var elementAsString = mapper.writeValueAsString(element);

        mvc.perform(post("/api/elements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(elementAsString))
                .andDo(print())
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

        var elementAsString = mapper.writeValueAsString(element);

        when(elementService.get(id)).thenReturn(element);

        mvc.perform(get("/api/elements/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(elementAsString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("username"))
                .andExpect(jsonPath("$.category").value(Category.FIRST.toString()));
    }

    @Test
    @SneakyThrows
    public void testGetElement_ElementNotFound() {
        long id = 1L;

        doThrow(new EntityNotFoundException("Entity not found")).when(elementService).get(id);

        mvc.perform(get("/api/elements/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
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
                        .param("category", Category.FIRST.toString())
                        .contentType(MediaType.APPLICATION_JSON))
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
        var elementToUpdate = new Element();
        elementToUpdate.setId(id);
        elementToUpdate.setName("username1");
        elementToUpdate.setCategory(Category.FIRST);

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
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testDelete_shouldReturnStatusCodeOk() {
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
}