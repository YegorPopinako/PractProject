package ua.petproject.categoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.petproject.model.Element;
import ua.petproject.repository.ElementDAO;

import java.util.NoSuchElementException;

@Service
public class ElementServiceImpl implements ElementService {

    @Autowired
    private ElementDAO elementDAO;

    @Override
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

    @Override
    public Element updateElement(Long id, Element element) {
        if (elementDAO.existsById(id)) {
            element.setId(id);
            return elementDAO.save(element);
        }
        return null;
    }
}
