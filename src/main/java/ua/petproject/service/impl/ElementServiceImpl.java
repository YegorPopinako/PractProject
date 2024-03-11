package ua.petproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.petproject.model.Element;
import ua.petproject.model.categories.Category;
import ua.petproject.repository.ElementRepository;
import ua.petproject.service.ElementService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
public class ElementServiceImpl implements ElementService {

    private ElementRepository elementRepository;

    @Override
    @Transactional(readOnly = true)
    public Element getElement(Long id) {
        return elementRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Element not found"));
    }

    @Override
    public void deleteElement(Long id) {
        elementRepository.deleteById(id);
    }

    @Override
    public Element addElement(Element element) {
        return elementRepository.save(element);
    }

    @Override //TODO: remind how to rewrite
    public Element updateElement(Long id, Element element) {
        Element existingElement = getElement(id);
        if (existingElement != null) {
            existingElement.setName(element.getName());
            existingElement.setCategory(element.getCategory());
            return elementRepository.save(existingElement);
        }
        return null;
    }

    @Override
    public List<Element> getAllByCategory(Category category) {
        return elementRepository.findByCategory(category);
    }
}
