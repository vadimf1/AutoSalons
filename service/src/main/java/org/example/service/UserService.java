package org.example.service;

import org.example.dto.request.UserRequestDto;
import org.example.dto.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    void addUser(UserRequestDto userDto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(int userId);
    void updateUser(int id, UserRequestDto updatedUserDto);
    void deleteUserById(int userId);
    UserDetails loadUserByUsername(String username);
}