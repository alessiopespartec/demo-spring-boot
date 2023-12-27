package com.example.demo.book;

import com.example.demo.ResponseHandler;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;

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
        List<Book> data = bookService.getAllBooks();
        if (data.isEmpty()) {
            return ResponseHandler.generateResponse("Books not found", HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.generateResponse("Books request successful", HttpStatus.OK, data);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        if (book == null) {
            return ResponseHandler.generateResponse("Book not found", HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.generateResponse("Book request successful", HttpStatus.OK, book);
    }

    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return ResponseHandler.generateResponse("Book added successfully", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateBook(@RequestBody Book book, @PathVariable Long id) {
        try {
            bookService.updateBook(book, id);
            return ResponseHandler.generateResponse("Book updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id){
        try {
            bookService.deleteBook(id);
            return ResponseHandler.generateResponse("Book deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String error = "Invalid parameter: " + e.getName() + " should be of type " + e.getRequiredType().getSimpleName();
        return ResponseHandler.generateResponse(error, HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e) {
        return ResponseHandler.generateResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}
