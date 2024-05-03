package ua.petproject.controller;

import jakarta.validation.Valid;
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
import ua.petproject.models.Element;
import ua.petproject.models.enums.ElementCategory;
import ua.petproject.service.ElementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/elements")
public class ElementController {

    private final ElementService elementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Element add(@Valid @RequestBody Element element) {
        return elementService.add(element);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Element get(@PathVariable Long id) {
        return elementService.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Element> getAll(@RequestParam ElementCategory elementCategory) {
        return elementService.getAll(elementCategory);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Element update(@PathVariable Long id, @Valid @RequestBody Element element) {
        return elementService.update(id, element);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        elementService.delete(id);
    }
}