package ua.petproject.controller;

import ua.petproject.categories.Category;
import ua.petproject.categoryservice.CategoryService;
import ua.petproject.model.Element;

public class CategoryController {
    private CategoryService categoryService;
    private Category category;

    public Element getElement(long id) {
        return categoryService.getElement(id);
    }

    public void deleteElement(long id) {
        categoryService.deleteElement(id);
    }

    public Element addElement(Element element) {
        return categoryService.addElement(element);
    }

    public Element updateElement(long id, Element element) {
        return categoryService.updateElement(id, element);
    }
}
