package ua.petproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.petproject.model.Element;
import ua.petproject.categoryservice.ElementService;
import ua.petproject.model.categories.Category;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private ElementService elementService;

    @GetMapping("/{id}")
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
    public List<Element> getAllByCategory(@RequestParam Category category) {
        return elementService.getAllByCategory(category);
    }
}
