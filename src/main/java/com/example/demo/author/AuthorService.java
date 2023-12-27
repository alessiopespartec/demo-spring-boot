package com.example.demo.author;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepositoty authorRepositoty;

    @Autowired
    public AuthorService(AuthorRepositoty authorRepositoty) {
        this.authorRepositoty = authorRepositoty;
    }

    public List<Author> getAllAuthors() {
        return authorRepositoty.findAll();
    }

    public Author getAuthor(Long id) {
        return authorRepositoty.findById(id).orElse(null);
    }

    public void addAuthor(Author author) {
        authorRepositoty.save(author);
    }

    public void updateAuthor(Author author, Long id) {
        Author authorToUpdate = authorRepositoty.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with ID " + id));

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());

        authorRepositoty.save(authorToUpdate);
    }

    public void deleteAuthor(Long id) {
        authorRepositoty.deleteById(id);
    }
}
