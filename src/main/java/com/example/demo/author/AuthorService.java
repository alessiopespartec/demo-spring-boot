package com.example.demo.author;

import com.example.demo.book.Book;
import com.example.demo.publisher.Publisher;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return validateAndGetAuthorById(id);
    }

    public void addAuthor(Author author) {
        authorRepositoty.save(author);
    }

    public void updateAuthor(Author author, Long id) {
        Author authorToUpdate = validateAndGetAuthorById(id);

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());

        authorRepositoty.save(authorToUpdate);
    }

    public void deleteAuthor(Long id) {
        Author authorToDelete = validateAndGetAuthorById(id);
        authorRepositoty.deleteById(id);
    }

    private Author validateAndGetAuthorById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Author must have an ID");
        }

        return authorRepositoty.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with ID " + id));
    }
}
