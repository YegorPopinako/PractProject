package ua.petproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.petproject.service.ElementService;
import ua.petproject.model.Element;
import ua.petproject.model.categories.Category;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/elements")
public class ElementController {

    private final ElementService elementService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Element getElement(@PathVariable Long id) {
        return elementService.getElement(id);
    }

    @DeleteMapping("/{id}")
    public void deleteElement(@PathVariable Long id) {
        elementService.deleteElement(id);
    }

    @PostMapping
    public Element addElement(@RequestBody Element element) {
        return elementService.addElement(element);
    }

    @PutMapping("/{id}")
    public Element updateElement(@PathVariable Long id, @RequestBody Element element) {
        return elementService.updateElement(id, element);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public List<Element> getAllByCategory(@RequestParam Category category) {
        return elementService.getAllByCategory(category);
    }
}
