package ua.petproject.service;

import ua.petproject.models.Author;

public interface AuthorService {

    Author findOrCreateAuthor(String authorName);
}
