package com.usermanagement.user_management.service;

import com.usermanagement.user_management.model.User;
import com.usermanagement.user_management.utils.JsonStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private List<User> users = JsonStorage.loadUsers(); // Charger les données JSON au démarrage

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return users;
    }

    // Ajouter un utilisateur
    public User addUser(User user) {
        user.setId((long) (users.size() + 1)); // Génération d'un ID unique
        users.add(user);
        JsonStorage.saveUsers(users); // Enregistrer dans le fichier JSON
        return user;
    }

    // Mettre à jour un utilisateur
    public User updateUser(Long id, User updatedUser) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                JsonStorage.saveUsers(users); // Sauvegarde après modification
                return user;
            }
        }
        return null;
    }

    // Supprimer un utilisateur
    public boolean deleteUser(Long id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        if (removed) {
            JsonStorage.saveUsers(users); // Sauvegarde après suppression
        }
        return removed;
    }
}
