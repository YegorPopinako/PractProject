package ua.petproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/elements")
public class ElementController {

    private final ElementService elementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Element> add(@RequestBody Element element) {
        Element savedElement = elementService.add(element);
        return ResponseEntity.created(URI.create("/elements/" + savedElement.getId())).body(savedElement);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Element> get(@PathVariable Long id) {
        Element element = elementService.get(id);
        return ResponseEntity.ok(element);
    }

    @GetMapping
    public ResponseEntity<List<Element>> getAll(@RequestParam Category category) {
        List<Element> elements = elementService.getAll(category);
        if (elements.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(elements);
        }
    }



    @PutMapping("/{id}")
    public Element update(@PathVariable Long id, @RequestBody Element element) {
        return elementService.update(id, element);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        elementService.delete(id);
    }
}