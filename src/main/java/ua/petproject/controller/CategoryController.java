package ua.petproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.petproject.categoryservice.ElementService;
import ua.petproject.model.Element;

@Controller
@AllArgsConstructor
public class CategoryController {

    private ElementService elementService;

    public Element getElement(Long id) {
        return elementService.getElement(id);
    }

    public void deleteElement(Long id) {
        elementService.deleteElement(id);
    }

    public Element addElement(Element element) {
        return elementService.addElement(element);
    }

    public Element updateElement(Long id, Element element) {
        return elementService.updateElement(id, element);
    }

}
