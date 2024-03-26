package ua.petproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.petproject.models.Element;
import ua.petproject.models.categories.ElementCategory;
import ua.petproject.repository.ElementRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ElementServiceImplTest {

    @Mock
    private ElementRepository elementRepository;

    @InjectMocks
    private ElementServiceImpl elementService;

    @Test
    void elementService_AddElement() {
        Element element = new Element("Element 1", ElementCategory.FIRST);

        when(elementRepository.save(element)).thenReturn(element);

        Element result = elementService.add(element);
        assertEquals(element, result);
    }

    @Test
    void elementService_GetElementById() {
        long id = 1L;
        Element element = new Element("Element 1", ElementCategory.FIRST);

        when(elementRepository.findById(id)).thenReturn(Optional.ofNullable(element));

        Element result = elementService.get(id);

        assertEquals(element, result);
    }

    @Test
    void elementService_GetAllByCategory() {
        ElementCategory elementCategory = ElementCategory.FIRST;
        Element element = new Element("Element 1", elementCategory);
        Element element2 = new Element("Element 2", elementCategory);
        Element element3 = new Element("Element 3", elementCategory);

        when(elementRepository.findByElementCategory(elementCategory)).thenReturn(List.of(element, element2, element3));

        List<Element> result = elementService.getAll(elementCategory);
        assertEquals(List.of(element, element2, element3), result);
    }

    @Test
    void elementService_UpdateElement() {
        long id = 1L;
        Element originalElement = new Element("Element 1", ElementCategory.FIRST);
        Element updatedElement = new Element("Updated Element 1", ElementCategory.FIRST);

        when(elementRepository.findById(id)).thenReturn(Optional.ofNullable(originalElement));

        elementService.update(id, updatedElement);

        assertEquals(updatedElement, originalElement);
    }

    @Test
    void elementService_DeleteElement() {
        Long id = 4L;

        when(elementRepository.existsById(id)).thenReturn(true);

        assertAll(() -> elementService.delete(id));
    }

    @Test
    void elementService_GetElementById_ElementNotFound() {
        long id = 1L;

        when(elementRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> elementService.get(id));
    }

    @Test
    void elementService_UpdateElement_ElementNotFound() {
        long id = 1L;
        Element updatedElement = new Element("Updated Element 1", ElementCategory.FIRST);

        when(elementRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> elementService.update(id, updatedElement));
    }

    @Test
    void elementService_DeleteElement_ElementNotFound() {
        Long id = 4L;

        when(elementRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> elementService.delete(id));
    }

    @Test
    void elementService_AddElement_NullElement() {
        assertThrows(NullPointerException.class, () -> elementService.add(null));
    }
}