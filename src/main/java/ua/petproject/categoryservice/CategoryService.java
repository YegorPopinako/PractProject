package ua.petproject.categoryservice;

import ua.petproject.model.Element;

public interface CategoryService {

    Element getElement(long id);

    void deleteElement(long id);

    Element addElement(Element element);

    Element updateElement(long id, Element element);

}
