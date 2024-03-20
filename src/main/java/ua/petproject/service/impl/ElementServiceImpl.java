package ua.petproject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.petproject.model.Element;
import ua.petproject.model.categories.Category;
import ua.petproject.repository.ElementRepository;
import ua.petproject.service.ElementService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ElementServiceImpl implements ElementService {

    private ElementRepository elementRepository;

    @Override
    @Transactional
    public Element add(Element element) {
        if(element == null){
            throw new NullPointerException("Element can't be null");
        }
        return elementRepository.save(element);
    }

    @Override
    public Element get(Long id) {
        return elementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Override
    public List<Element> getAll(Category category) {
        return elementRepository.findByCategory(category);
    }

    @Override
    @Transactional
    public Element update(Long id, Element element) {
        try {
            Element existingElement = get(id);
            existingElement.setName(element.getName());
            existingElement.setCategory(element.getCategory());
            return existingElement;
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Entity with ID " + id + " not found.");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!elementRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found");
        }
        elementRepository.deleteById(id);
    }
}
