package com.ditech.backend.service.impl;

import com.ditech.backend.dto.UserDto;
import com.ditech.backend.model.User;
import com.ditech.backend.repository.UserRepository;
import com.ditech.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     * @param user Usuario a guardar.
     * @return Usuario guardado.
     */
    public UserDto createUser(UserDto user) {
        User userToEntity = modelMapper.map(user, User.class);
        User UserToDto = userRepository.save(userToEntity);
        return modelMapper.map(UserToDto, UserDto.class);
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     * @return Lista de usuarios.
     */
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        Type targetListType = new TypeToken<List<UserDto>>() {}.getType();
        return modelMapper.map(users, targetListType);
    }

    /**
     * Busca un usuario por su ID.
     * @param id ID del usuario a buscar.
     * @return Usuario encontrado o vacío si no existe.
     */
    public Optional<UserDto> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return Optional.ofNullable(userDto);
    }

    /**
     * Elimina un usuario por su ID.
     * @param id ID del usuario a eliminar.
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
