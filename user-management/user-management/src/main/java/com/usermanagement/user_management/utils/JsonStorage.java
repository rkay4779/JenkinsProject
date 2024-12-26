package com.usermanagement.user_management.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.user_management.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage {

    private static final String FILE_PATH = "users.json"; // JSON file path
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Load users from the JSON file
    public static List<User> loadUsers() {
        File file = new File(FILE_PATH);
        System.out.println("Resolved file path (loadUsers): " + file.getAbsolutePath());
        try {
            if (file.exists()) {
                System.out.println("Loading users from JSON file...");
                return objectMapper.readValue(file, new TypeReference<List<User>>() {});
            } else {
                System.out.println("JSON file does not exist. Returning an empty list.");
            }
        } catch (IOException e) {
            System.err.println("Error reading users.json file: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // Save users to the JSON file
    public static void saveUsers(List<User> users) {
        File file = new File(FILE_PATH);
        System.out.println("Resolved file path (saveUsers): " + file.getAbsolutePath());
        try {
            objectMapper.writeValue(file, users);
            System.out.println("Users saved successfully to the JSON file.");
        } catch (IOException e) {
            System.err.println("Error saving users to JSON file: " + e.getMessage());
        }
    }
}
