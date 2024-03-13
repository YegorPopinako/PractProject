package ua.petproject.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.petproject.model.Element;
import ua.petproject.model.categories.Category;
import ua.petproject.repository.ElementRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElementServiceImplTest {

    @Mock
    private ElementRepository elementRepository;

    @InjectMocks
    private ElementServiceImpl elementService;

    @Test
    void ElementService_AddElement() {
        Element element = new Element("Element 1", Category.FIRST);

        when(elementRepository.save(element)).thenReturn(element);

        Element result = elementService.add(element);
        assertEquals(element, result);
    }

    @Test
    void ElementService_GetElementById() {
        long id = 1L;
        Element element = new Element("Element 1", Category.FIRST);

        when(elementRepository.findById(id)).thenReturn(Optional.ofNullable(element));

        Element result = elementService.get(id);

        assertEquals(element, result);
    }

    @Test
    void ElementService_GetAllByCategory() {
        Category category = Category.FIRST;
        Element element = new Element("Element 1", category);
        Element element2 = new Element("Element 2", category);
        Element element3 = new Element("Element 3", category);

        when(elementRepository.findByCategory(category)).thenReturn(List.of(element, element2, element3));

        List<Element> result = elementService.getAll(category);
        assertEquals(List.of(element, element2, element3), result);
    }

    @Test
    void ElementService_UpdateElement() {
        long id = 1L;
        Element originalElement = new Element("Element 1", Category.FIRST);
        Element updatedElement = new Element("Updated Element 1", Category.FIRST);

        when(elementRepository.findById(id)).thenReturn(Optional.ofNullable(originalElement));

        elementService.update(id, updatedElement);

        assertEquals(updatedElement, originalElement);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        Element element = new Element("Element 1", Category.FIRST);

        when(elementRepository.findById(id)).thenReturn(Optional.ofNullable(element));

        assertAll(() -> elementService.delete(id));
    }
}