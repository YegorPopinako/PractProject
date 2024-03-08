package ua.petproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.petproject.categoryservice.CategoryService;
import ua.petproject.model.Element;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public Element getElement(Long id) {
        return categoryService.getElement(id);
    }

    public void deleteElement(Long id) {
        categoryService.deleteElement(id);
    }

    public Element addElement(Element element) {
        return categoryService.addElement(element);
    }

    public Element updateElement(Long id, Element element) {
        return categoryService.updateElement(id, element);
    }

}
