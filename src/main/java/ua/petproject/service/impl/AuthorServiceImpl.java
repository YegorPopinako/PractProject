package ua.petproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.petproject.models.Author;
import ua.petproject.repository.AuthorRepository;
import ua.petproject.service.AuthorService;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author findOrCreateAuthor(String authorName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            author = authorRepository.save(new Author(authorName));
        }
        return author;
    }
}
