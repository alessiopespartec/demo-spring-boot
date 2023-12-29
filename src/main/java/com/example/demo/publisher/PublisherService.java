package com.example.demo.publisher;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher getPublisher(Long id) {
        return findPublisherById(id);
    }

    public void addPublisher(Publisher publisher) {
        validatePublisherName(publisher.getName()); // Check if empty name
        publisherRepository.save(publisher);
    }

    public void updatePublisher(Publisher publisher, Long id) {
        validatePublisherName(publisher.getName()); // Check if empty name
        Publisher publisherToUpdate = findPublisherById(id); // Check if exists in database

        publisherToUpdate.setName(publisher.getName());
        publisherRepository.save(publisherToUpdate);
    }

    public void deletePublisher(Long id) {
        Publisher publisherToDelete = findPublisherById(id);
        publisherRepository.deleteById(id);
    }

    private Publisher findPublisherById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Publisher must have an ID");
        }
        return publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found with ID " + id));
    }

    private void validatePublisherName(String name) {
        if (name.trim().isEmpty() || name.trim() == null) {
            throw new IllegalArgumentException("Publisher name cannot be null or empty");
        }
    }
}
