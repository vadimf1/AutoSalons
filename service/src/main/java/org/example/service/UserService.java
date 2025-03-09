package org.example.service;

import org.example.dto.UserDto;
import org.example.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    void addUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getUserById(int userId);
    void updateUser(UserDto updatedUserDto);
    void deleteUserById(int userId);
    UserDetails loadUserByUsername(String username);
}