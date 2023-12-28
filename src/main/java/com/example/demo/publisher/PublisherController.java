package com.example.demo.publisher;

import com.example.demo.ResponseHandler;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllPublishers() {
        List<Publisher> data = publisherService.getAllPublishers();
        if (data.isEmpty()) {
            return ResponseHandler.generateResponse("Publishers not found", HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.generateResponse("Publishers request successful", HttpStatus.FOUND, data);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getPublisher(@PathVariable Long id) {
        try {
            Publisher publisher = publisherService.getPublisher(id);
            return ResponseHandler.generateResponse("Publisher request successful", HttpStatus.FOUND, publisher);
        } catch (EntityNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addPublisher(@RequestBody Publisher publisher) {
        publisherService.addPublisher(publisher);
        return ResponseHandler.generateResponse("Publisher added successfully", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updatePublisher(@RequestBody Publisher publisher, @PathVariable Long id) {
        try {
            publisherService.updatePublisher(publisher, id);
            return ResponseHandler.generateResponse("Publisher updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletePublisher(@PathVariable Long id) {
        try {
            publisherService.deletePublisher(id);
            return ResponseHandler.generateResponse("Publisher deleted successfully", HttpStatus.OK);
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
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}
