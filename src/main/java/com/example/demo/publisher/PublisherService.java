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
        return validateAndGetPublisherById(id);
    }

    public void addPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void updatePublisher(Publisher publisher, Long id) {
        Publisher publisherToUpdate = validateAndGetPublisherById(id);

        publisherToUpdate.setName(publisher.getName());
        publisherRepository.save(publisherToUpdate);
    }

    public void deletePublisher(Long id) {
        Publisher publisherToDelete = validateAndGetPublisherById(id);
        publisherRepository.deleteById(id);
    }

    private Publisher validateAndGetPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found with ID " + id));
    }
}
