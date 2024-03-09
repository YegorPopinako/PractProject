package ua.petproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.petproject.model.Element;
import ua.petproject.categoryservice.ElementService;
import ua.petproject.model.categories.Category;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private ElementService elementService;

    @GetMapping("/api/categories/{id}")
    public Element getElement(@PathVariable Long id) {
        return elementService.getElement(id);
    }

    @DeleteMapping("/api/categories/{id}")
    public void deleteElement(@PathVariable Long id) {
        elementService.deleteElement(id);
    }

    @PostMapping
    public Element addElement(@RequestBody Element element) {
        return elementService.addElement(element);
    }

    @PutMapping("/api/categories/{id}")
    public Element updateElement(@PathVariable Long id, @RequestBody Element element) {
        return elementService.updateElement(id, element);
    }

    @GetMapping("/api/categories")
    public List<Element> getAllByCategory(@RequestParam Category category) {
        return elementService.getAllByCategory(category);
    }
}
