package com.ditech.backend.service;

import com.ditech.backend.dto.UserDto;
import com.ditech.backend.model.User;
import com.ditech.backend.repository.UserRepository;
import com.ditech.backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, modelMapper);
    }



    @Test
    void saveUser_shouldInvokeRepositorySaveOnceAndReturnUserWithId() {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setEmail("testuser@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.createUser(user);

        verify(userRepository, times(1)).save(any(User.class));
        assert result.getId() != null;
        assert result.getId().equals(1L);
    }

    @Test
    void getUserById_whenUserNotFound_shouldThrowEntityNotFoundException() {
        Long userId = 3L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userId));
    }
}
