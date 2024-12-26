package com.usermanagement.user_management.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.user_management.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage {

    private static final String FILE_PATH = "users.json"; // Chemin du fichier JSON
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Charger les utilisateurs depuis le fichier JSON
    public static List<User> loadUsers() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<User>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // Enregistrer les utilisateurs dans le fichier JSON
    public static void saveUsers(List<User> users) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
