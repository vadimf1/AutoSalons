package org.example.service;

import org.example.dto.request.UserProfileUpdateDto;
import org.example.dto.request.UserRequestDto;
import org.example.dto.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    void addUser(UserRequestDto userDto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(int userId);
    void updateUser(int id, UserRequestDto userDto);
    void updateUserProfile(int id, UserProfileUpdateDto userProfileDto);
    void deleteUserById(int userId);
}