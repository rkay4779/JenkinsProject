/*package com.usermanagement.user_management.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.user_management.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private ObjectMapper objectMapper;

    private final String JSON_FILE_PATH = "src/main/resources/users.json";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUsers() throws Exception {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User(1L, "John Doe", "john.doe@example.com"));

        when(objectMapper.readValue(any(File.class), eq(new TypeReference<List<User>>() {}))).thenReturn(mockUsers);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));

        verify(objectMapper, times(1)).readValue(any(File.class), eq(new TypeReference<List<User>>() {}));
    }

    @Test
    public void testAddUser() throws Exception {
        List<User> mockUsers = new ArrayList<>();
        User newUser = new User(null, "Jane Doe", "jane.doe@example.com");
        User savedUser = new User(1L, "Jane Doe", "jane.doe@example.com");

        mockUsers.add(savedUser);

        when(objectMapper.readValue(any(File.class), eq(new TypeReference<List<User>>() {}))).thenReturn(new ArrayList<>());
        doNothing().when(objectMapper).writerWithDefaultPrettyPrinter().writeValue(any(File.class), eq(mockUsers));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));

        verify(objectMapper, times(1)).readValue(any(File.class), eq(new TypeReference<List<User>>() {}));
        verify(objectMapper, times(1)).writerWithDefaultPrettyPrinter().writeValue(any(File.class), eq(mockUsers));
    }

    @Test
    public void testDeleteUser() throws Exception {
        List<User> mockUsers = new ArrayList<>();
        User existingUser = new User(1L, "John Doe", "john.doe@example.com");
        mockUsers.add(existingUser);

        when(objectMapper.readValue(any(File.class), eq(new TypeReference<List<User>>() {}))).thenReturn(mockUsers);
        doNothing().when(objectMapper).writerWithDefaultPrettyPrinter().writeValue(any(File.class), anyList());

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully."));

        verify(objectMapper, times(1)).readValue(any(File.class), eq(new TypeReference<List<User>>() {}));
        verify(objectMapper, times(1)).writerWithDefaultPrettyPrinter().writeValue(any(File.class), anyList());
    }

   /* @Test
    public void testDeleteUserNotFound() throws Exception {
        when(objectMapper.readValue(any(File.class), eq(new TypeReference<List<User>>() {}))).thenReturn(new ArrayList<>());

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User not found."));

        verify(objectMapper, times(1)).readValue(any(File.class), eq(new TypeReference<List<User>>() {}));
        verify(objectMapper, never()).writerWithDefaultPrettyPrinter().writeValue(any(File.class), anyList());
    }
}*/
package com.usermanagement.user_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.user_management.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testAddUser() throws Exception {
        User newUser = new User(null, "John Doe", "john.doe@example.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }
    @Test
    void testUpdateUser() throws Exception {
        User newUser = new User(null, "Jane Smith", "jane.smith@example.com");

        // Add a new user first
        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        User createdUser = objectMapper.readValue(response, User.class);

        // Update the user details
        User updatedUser = new User(createdUser.getId(), "Jane Doe", "jane.doe@example.com");

        mockMvc.perform(put("/users/{id}", createdUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }


    @Test
    void testDeleteUser() throws Exception {
        User newUser = new User(null, "Jane Doe", "jane.doe@example.com");

        // Add a new user first
        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        User createdUser = objectMapper.readValue(response, User.class);

        // Delete the user
        mockMvc.perform(delete("/users/{id}", createdUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully."));
    }
}
