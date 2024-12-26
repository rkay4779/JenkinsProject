package com.usermanagement.user_management.service;

import com.usermanagement.user_management.model.User;
import com.usermanagement.user_management.utils.JsonStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    // Retrieve all users from the JSON file
    public List<User> getAllUsers() {
        return JsonStorage.loadUsers(); // Always dynamically load the latest data
    }

    // Add a new user
    public User addUser(User user) {
        List<User> users = JsonStorage.loadUsers(); // Load current users
        // Generate a unique ID (ensure uniqueness even if some IDs are deleted)
        long newId = users.stream().mapToLong(User::getId).max().orElse(0) + 1;
        user.setId(newId);
        users.add(user);
        JsonStorage.saveUsers(users); // Save to JSON file
        return user;
    }

    // Update an existing user by ID
    public User updateUser(Long id, User updatedUser) {
        List<User> users = JsonStorage.loadUsers(); // Load current users
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                JsonStorage.saveUsers(users); // Save changes to JSON file
                return user;
            }
        }
        return null; // Return null if the user is not found
    }

    // Delete a user by ID
    public boolean deleteUser(Long id) {
        List<User> users = JsonStorage.loadUsers(); // Load current users
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        if (removed) {
            JsonStorage.saveUsers(users); // Save changes to JSON file
        }
        return removed;
    }
}
