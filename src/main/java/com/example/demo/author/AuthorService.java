package com.example.demo.author;

import com.example.demo.exceptions.EmptyOrNullFieldException;
import com.example.demo.exceptions.MessageFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
        return findAuthorById(id);
    }

    public void addAuthor(Author author) {
        validateAuthor(author.getFirstName(), author.getLastName());
        authorRepositoty.save(author);
    }

    public void updateAuthor(Author author, Long id) {
        validateAuthor(author.getFirstName(), author.getLastName());
        Author authorToUpdate = findAuthorById(id);

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());

        authorRepositoty.save(authorToUpdate);
    }

    public void deleteAuthor(Long id) {
        authorRepositoty.delete(findAuthorById(id));
    }

    private Author findAuthorById(Long id) {
        return authorRepositoty.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFactory.entityNotFoundMessage("Author", id)));
    }

    private void validateAuthor(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new EmptyOrNullFieldException(MessageFactory.emptyOrNullFieldMessage("Author", "first name"));
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new EmptyOrNullFieldException(MessageFactory.emptyOrNullFieldMessage("Author", "last name"));
        }
    }
}
