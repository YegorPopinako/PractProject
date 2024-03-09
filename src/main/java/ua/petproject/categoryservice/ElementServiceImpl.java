package ua.petproject.categoryservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.petproject.model.Element;
import ua.petproject.model.categories.Category;
import ua.petproject.repository.ElementDAO;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
public class ElementServiceImpl implements ElementService {

    private ElementDAO elementDAO;

    @Override
    @Transactional(readOnly = true)
    public Element getElement(Long id) {
        return elementDAO.findById(id).orElseThrow(() -> new NoSuchElementException("Element not found"));
    }

    @Override
    public void deleteElement(Long id) {
        elementDAO.deleteById(id);
    }

    @Override
    public Element addElement(Element element) {
        return elementDAO.save(element);
    }

    @Override //TODO: remind how to rewrite
    public Element updateElement(Long id, Element element) {
        Element existingElement = getElement(id);
        if (existingElement != null) {
            existingElement.setName(element.getName());
            existingElement.setCategory(element.getCategory());
            return elementDAO.save(existingElement);
        }
        return null;
    }

    @Override
    public List<Element> getAllByCategory(Category category) {
        return elementDAO.findByCategory(category);
    }
}
