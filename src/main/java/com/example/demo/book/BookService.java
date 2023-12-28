package com.example.demo.book;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorRepositoty;
import com.example.demo.publisher.Publisher;
import com.example.demo.publisher.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    private final AuthorRepositoty authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PublisherRepository publisherRepository, AuthorRepositoty authorRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Long bookId) {
        return validateAndGetBookById(bookId);
    }

    @Transactional
    public void addBook(Book book) {
        Publisher publisher = validateAndGetPublisher(book.getPublisher());
        book.setPublisher(publisher);

        Set<Author> authors = validateAndGetAuthors(book.getAuthors());
        book.setAuthors(authors);

        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(Book book, Long bookId) {
        Publisher publisher = validateAndGetPublisher(book.getPublisher());
        Set<Author> authors = validateAndGetAuthors(book.getAuthors());

        Book bookToUpdate = validateAndGetBookById(bookId);

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setYear(book.getYear());
        bookToUpdate.setPublisher(publisher);
        bookToUpdate.setAuthors(authors);

        bookRepository.save(bookToUpdate);
    }

    public void deleteBook(Long bookId) {
        Book bookToDelete = validateAndGetBookById(bookId);
        bookRepository.deleteById(bookId);
    }

    private Book validateAndGetBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID " + bookId));
    }

    private Publisher validateAndGetPublisher(Publisher publisher) {
        if (publisher == null || publisher.getId() == null) {
            throw new IllegalArgumentException("Publisher must have an ID");
        }

        return publisherRepository.findById(publisher.getId())
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found with ID " + publisher.getId()));
    }

    private Set<Author> validateAndGetAuthors(Set<Author> authors) {
        Set<Author> validatedAuthors = new HashSet<>();
        for (Author author : authors) {
            if (author.getId() == null) {
                throw new IllegalArgumentException("Each author must have an ID");
            }
            Author dbAuthor = authorRepository.findById(author.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Author not found with ID " + author.getId()));
            validatedAuthors.add(dbAuthor);
        }
        return validatedAuthors;
    }
}
