package com.example.demo.service;

import com.example.demo.exceptions.MessageFactory;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return findUserById(id);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user, Long id) {
        User userToUpdate = findUserById(id);

        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
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

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFactory.entityNotFoundMessage("User", id)));
    }
}
