package com.example.demo.author;

import com.example.demo.ResponseHandler;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllAuthors() {
        List<Author> data = authorService.getAllAuthors();
        if (data.isEmpty()) {
            return ResponseHandler.generateResponse("Authors not found", HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.generateResponse("Authors request successful", HttpStatus.OK, data);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getAuthor(@PathVariable Long id){
        Author authorToFind = authorService.getAuthor(id);
        if (authorToFind == null) {
            return ResponseHandler.generateResponse("Author not found", HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.generateResponse("Author request successful", HttpStatus.OK, authorToFind);
    }

    @PostMapping
    public ResponseEntity<Object> addAuthor(@RequestBody Author author) {
        authorService.addAuthor(author);
        return ResponseHandler.generateResponse("Author added successfully", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateAuthor(@RequestBody Author author, @PathVariable Long id) {
        authorService.updateAuthor(author, id);
        return ResponseHandler.generateResponse("Author updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseHandler.generateResponse("Author deleted successfully", HttpStatus.OK);
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
