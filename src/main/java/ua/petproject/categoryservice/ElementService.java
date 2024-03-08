package ua.petproject.categoryservice;

import jakarta.annotation.Nullable;
import ua.petproject.model.Element;
import ua.petproject.model.categories.Category;

import java.util.List;

public interface ElementService {

    Element getElement(Long id);

    void deleteElement(Long id);

    Element addElement(Element element);

    Element updateElement(Long id, Element element);

    @Nullable
    List<Element> getAllByCategory(Category category);
}
