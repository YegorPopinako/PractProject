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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElementControllerTest {

    @Mock
    private ElementService elementService;

    @InjectMocks
    private ElementController elementController;

    @Test
    public void elementController_addNewElement()  {
        Element elementToAdd = new Element("Test Element", Category.SECOND);
        Element expectedSavedElement = new Element("Test Element", Category.SECOND);

        when(elementService.add(elementToAdd)).thenReturn(expectedSavedElement);

        ResponseEntity<Element> response = elementController.add(elementToAdd);

        assertThat(response).isInstanceOf(ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = response.getHeaders().getLocation();
        assertThat(location).isEqualTo(URI.create("/elements/" + expectedSavedElement.getId()));

        assertThat(response.getBody()).isEqualTo(expectedSavedElement);
    }

    @Test
    public void elementController_getById() {
        Long id = 1L;
        Element expectedElement = new Element("Element 1", Category.FIRST);

        when(elementService.get(id)).thenReturn(expectedElement);

        ResponseEntity<Element> response = elementController.get(id);

        assertThat(response).isInstanceOf(ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedElement);
    }
}