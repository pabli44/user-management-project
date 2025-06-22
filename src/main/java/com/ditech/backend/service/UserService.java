package com.ditech.backend.service;

import com.ditech.backend.dto.UserDto;
import com.ditech.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto createUser(UserDto user);

    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Long id);

    void deleteUserById(Long id);

}
