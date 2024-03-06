package ua.petproject.categoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.petproject.model.Element;
import ua.petproject.repository.CategoryDAO;

@Service
public class CategoryServiceIMPL implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public Element getElement(long id) {
        return categoryDAO.findById(id).orElse(null);
    }

    @Override
    public void deleteElement(long id) {
        categoryDAO.deleteById(id);
    }

    @Override
    public Element addElement(Element element) {
        return categoryDAO.save(element);
    }

    @Override
    public Element updateElement(long id, Element element) {
        if (categoryDAO.existsById(id)) {
            element.setId(id);
            return categoryDAO.save(element);
        }
        return null;
    }
}
