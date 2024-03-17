package ua.petproject.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.petproject.model.Element;
import ua.petproject.model.categories.Category;
import ua.petproject.service.ElementService;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElementControllerTest {

    @Mock
    private ElementService elementService;

    @InjectMocks
    private ElementController elementController;

    @Test
    public void elementController_addNewElement() {
        Element elementToAdd = new Element("Test Element", Category.SECOND);
        Element expectedSavedElement = new Element("Test Element", Category.SECOND);

        when(elementService.add(elementToAdd)).thenReturn(expectedSavedElement);

        Element response = elementController.add(elementToAdd);

        assertThat(response).isEqualTo(expectedSavedElement);
    }

    @Test
    public void elementController_getById() {
        Long id = 1L;
        Element expectedElement = new Element("Element 1", Category.FIRST);

        when(elementService.get(id)).thenReturn(expectedElement);

        Element response = elementController.get(id);

        assertThat(response).isEqualTo(expectedElement);
    }

    @Test
    public void elementController_getElementsByCategory() {
        Category expectedCategory = Category.FIRST;
        List<Element> expectedElements = Arrays.asList(new Element("Element 1", Category.FIRST),
                new Element("Element 2", Category.FIRST));

        when(elementService.getAll(expectedCategory)).thenReturn(expectedElements);

        List<Element> response = elementController.getAll(expectedCategory);

        assertThat(response).isEqualTo(expectedElements);
    }

    @Test
    public void elementController_getElementsByCategory_EmptyList() {
        Category category = Category.FIRST;

        when(elementService.getAll(category)).thenReturn(Collections.emptyList());

        List<Element> response = elementController.getAll(category);

        assertThat(response).isEmpty();
    }

    @Test
    public void elementController_updateElement() {
        long id = 1L;
        Element updatedElement = new Element("Updated Element 1", Category.FIRST);

        when(elementService.update(id, updatedElement)).thenReturn(updatedElement);

        Element response = elementController.update(id, updatedElement);

        assertThat(response).isEqualTo(updatedElement);
    }

    @Test
    public void elementController_deleteElement() {
        long id = 1L;

        elementController.delete(id);

    }
}