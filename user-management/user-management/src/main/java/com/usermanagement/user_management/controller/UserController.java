package com.usermanagement.user_management.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.user_management.model.User;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final String JSON_FILE_PATH = "src/main/resources/users.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Charger les utilisateurs depuis le fichier JSON
    private List<User> loadUsers() {
        try {
            File file = new File(JSON_FILE_PATH);
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<User>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // Enregistrer les utilisateurs dans le fichier JSON
    private void saveUsers(List<User> users) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        List<User> users = loadUsers();

        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                saveUsers(users);
                return user;
            }
        }
        throw new RuntimeException("User not found");
    }


    @GetMapping
    public List<User> getUsers() {
        return loadUsers();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        List<User> users = loadUsers();
        user.setId(users.isEmpty() ? 1 : users.get(users.size() - 1).getId() + 1); // Générer un nouvel ID
        users.add(user);
        saveUsers(users);
        return user;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        List<User> users = loadUsers();
        if (users.removeIf(user -> user.getId().equals(id))) {
            saveUsers(users);
            return "User deleted successfully.";
        }
        return "User not found.";
    }
}
