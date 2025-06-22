package com.ditech.backend.controller;

import com.ditech.backend.dto.UserDto;
import com.ditech.backend.repository.UserRepository;
import com.ditech.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserController userController;



    @Test
    void getAllUsers_shouldReturnListOfUsersAndStatus200() throws Exception {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");

        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[0].email").value("testuser@example.com"));
    }

    @Test
    void createUser_shouldReturnCreatedUserAndStatus201() throws Exception {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setUsername("newuser");
        user.setEmail("newuser@example.com");

        UserDto savedUser = new UserDto();
        savedUser.setId(1L);
        savedUser.setUsername("newuser");
        savedUser.setEmail("newuser@example.com");

        when(userService.createUser(user)).thenReturn(savedUser);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}