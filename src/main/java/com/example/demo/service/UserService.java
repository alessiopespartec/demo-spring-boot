package com.example.demo.service;

import com.example.demo.exceptions.MessageFactory;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return findUserById(id);
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user, Long id) {
        User userToUpdate = findUserById(id);

        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setProfilePhotoUrl(user.getProfilePhotoUrl());
        userToUpdate.setCountry(user.getCountry());
        userToUpdate.setOccupation(user.getOccupation());
        userToUpdate.setDateOfBirth(user.getDateOfBirth());
        userToUpdate.setGender(user.getGender());
        userToUpdate.setRole(user.getRole());

        userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        userRepository.delete(findUserById(id));
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFactory.entityNotFoundMessage("User", id)));
    }

    @PostConstruct
    public void createDefaultUser() {
        if (userRepository.count() == 0) {
            User user = new User("admin@example.com", passwordEncoder.encode("password123"));
            userRepository.save(user);
        }
    }
}
