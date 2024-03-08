package ua.petproject.categoryservice;

import ua.petproject.model.Element;

public interface CategoryService {

    Element getElement(Long id);

    void deleteElement(Long id);

    Element addElement(Element element);

    Element updateElement(Long id, Element element);

}
