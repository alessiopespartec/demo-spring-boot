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
        validatePublisherName(publisher.getName());
        publisherRepository.save(publisher);
    }

    public void updatePublisher(Publisher publisher, Long id) {
        validatePublisherName(publisher.getName());
        Publisher publisherToUpdate = findPublisherById(id);

        publisherToUpdate.setName(publisher.getName());
        publisherRepository.save(publisherToUpdate);
    }

    public void deletePublisher(Long id) {
        Publisher publisherToDelete = findPublisherById(id);
        publisherRepository.deleteById(id);
    }

    private Publisher findPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found with ID " + id));
    }

    private void validatePublisherName(String name) {
        if (name.trim().isEmpty() || name.trim() == null) {
            throw new IllegalArgumentException("Publisher name cannot be null or empty");
        }
    }
}
