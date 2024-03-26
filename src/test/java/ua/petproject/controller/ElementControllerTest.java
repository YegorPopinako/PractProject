package ua.petproject.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.petproject.models.Element;
import ua.petproject.models.categories.ElementCategory;
import ua.petproject.service.ElementService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElementControllerTest {

    @Mock
    private ElementService elementService;

    @InjectMocks
    private ElementController elementController;

    @Test
    public void elementController_addNewElement() {
        Element elementToAdd = new Element("Test Element", ElementCategory.SECOND);
        Element expectedSavedElement = new Element("Test Element", ElementCategory.SECOND);

        when(elementService.add(elementToAdd)).thenReturn(expectedSavedElement);

        Element response = elementController.add(elementToAdd);

        assertThat(response).isEqualTo(expectedSavedElement);
    }

    @Test
    public void elementController_getById() {
        Long id = 1L;
        Element expectedElement = new Element("Element 1", ElementCategory.FIRST);

        when(elementService.get(id)).thenReturn(expectedElement);

        Element response = elementController.get(id);

        assertThat(response).isEqualTo(expectedElement);
    }

    @Test
    public void elementController_getElementsByCategory() {
        ElementCategory expectedElementCategory = ElementCategory.FIRST;
        List<Element> expectedElements = Arrays.asList(new Element("Element 1", ElementCategory.FIRST),
                new Element("Element 2", ElementCategory.FIRST));

        when(elementService.getAll(expectedElementCategory)).thenReturn(expectedElements);

        List<Element> response = elementController.getAll(expectedElementCategory);

        assertThat(response).isEqualTo(expectedElements);
    }

    @Test
    public void elementController_getElementsByCategory_EmptyList() {
        ElementCategory elementCategory = ElementCategory.FIRST;

        when(elementService.getAll(elementCategory)).thenReturn(Collections.emptyList());

        List<Element> response = elementController.getAll(elementCategory);

        assertThat(response).isEmpty();
    }

    @Test
    public void elementController_updateElement() {
        long id = 1L;
        Element updatedElement = new Element("Updated Element 1", ElementCategory.FIRST);

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