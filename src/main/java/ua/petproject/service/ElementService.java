package ua.petproject.service;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import ua.petproject.model.Element;
import ua.petproject.model.categories.Category;

import java.util.List;

/**
 * The ElementService interface provides methods that perform CRUD operations.
 */
public interface ElementService {

    /**
     * Adds a new element to the Database.
     *
     * @param element The element to be added.
     * @return The added element.
     */
    Element add(Element element);

    /**
     * Retrieves an element by its ID.
     *
     * @param id The ID of the element to retrieve.
     * @return The retrieved element.
     * @throws EntityNotFoundException If the element with the specified ID is not found.
     */
    Element get(Long id) throws EntityNotFoundException;

    /**
     * Retrieves all elements by category.
     *
     * @param category The category by which to filter the elements.
     * @return The list of retrieved elements.
     */
    List<Element> getAll(@Nullable Category category);

    /**
     * Updates an existing element.
     *
     * @param id The ID of the element to be updated.
     * @param element The new data for the element.
     * @return Updated element.
     */
    Element update(Long id, Element element);

    /**
     * Deletes an element from the system by its ID.
     *
     * @param id The ID of the element to be deleted.
     * @throws EntityNotFoundException If the element with the specified ID is not found.
     */
    void delete(Long id) throws EntityNotFoundException;
}

