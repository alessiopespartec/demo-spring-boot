package com.example.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Long id) {
        Optional<Book> bookToFind = bookRepository.findById(id);

        return bookToFind.orElse(null);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Book book, Long id) {
        Book bookToUpdate = bookRepository.getReferenceById(id);

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setYear(book.getYear());

        bookRepository.save(bookToUpdate);
    }

    public void deleteBook(Long id) {
        Book bookToDelete = bookRepository.getReferenceById(id);

        bookRepository.delete(bookToDelete);
    }
}
