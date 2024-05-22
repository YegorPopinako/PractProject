package ua.petproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.petproject.models.Author;
import ua.petproject.repository.AuthorRepository;
import ua.petproject.service.AuthorService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author findOrCreateAuthor(String authorName) {
        Optional<Author> author = Optional.ofNullable(authorRepository.findByName(authorName));
        return author.orElseGet(() -> authorRepository.save(new Author(authorName)));
    }
}
