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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElementControllerTest {

    @Mock
    private ElementService elementService;

    @InjectMocks
    private ElementController elementController;

    @Test
    public void testGetElementById() {
        Long id = 1L;
        Element expectedElement = new Element("Element 1", Category.FIRST);

        when(elementService.get(id)).thenReturn(expectedElement);

        ResponseEntity<Element> response = elementController.get(id);

        assertThat(response).isInstanceOf(ResponseEntity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedElement);
    }
}