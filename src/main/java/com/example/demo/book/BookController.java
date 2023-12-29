package com.example.demo.book;

import com.example.demo.exceptions.MessageFactory;
import com.example.demo.response.ResponseHandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            throw new EntityNotFoundException("No books found");
        }
        String successMessage = MessageFactory.successOperationMessage("Books", "retrieved");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK, books);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        String successMessage = MessageFactory.successOperationMessage("Book", "retrieved");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.FOUND, book);
    }

    @PostMapping
    public ResponseEntity<Object> addBook(@Valid @RequestBody Book book) {
        bookService.addBook(book);
        String successMessage = MessageFactory.successOperationMessage("Book", "added");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateBook(@Valid @RequestBody Book book, @PathVariable Long id) {
        bookService.updateBook(book, id);
        String successMessage = MessageFactory.successOperationMessage("Book", "updated");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        String successMessage = MessageFactory.successOperationMessage("Book", "deleted");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
    }
}
