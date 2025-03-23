package org.example.controller;

import org.example.dto.request.UserProfileUpdateDto;
import org.example.dto.request.UserRequestDto;
import org.example.dto.response.UserResponseDto;
import org.example.exception.ServiceException;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserRequestDto userRequestDto;
    private UserProfileUpdateDto userProfileUpdateDto;
    private UserResponseDto userResponseDto;
    private List<UserResponseDto> users;

    @BeforeEach
    void setUp() {
        userRequestDto = UserRequestDto.builder()
                .username("testuser")
                .password("password123")
                .build();

        userProfileUpdateDto = UserProfileUpdateDto.builder()
                .build();

        userResponseDto = UserResponseDto.builder()
                .id(1)
                .username("testuser")
                .build();

        users = Arrays.asList(userResponseDto);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(users, response.getBody());
        verify(userService).getAllUsers();
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userService.getUserById(anyInt())).thenReturn(userResponseDto);

        ResponseEntity<UserResponseDto> response = userController.getUserById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(userResponseDto, response.getBody());
        verify(userService).getUserById(1);
    }

    @Test
    void createUser_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = userController.createUser(userRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User added successfully", response.getBody());
        verify(userService).addUser(userRequestDto);
    }

    @Test
    void updateUser_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = userController.updateUser(1, userRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User updated successfully", response.getBody());
        verify(userService).updateUser(1, userRequestDto);
    }

    @Test
    void updateUserProfile_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = userController.updateUserProfile(1, userProfileUpdateDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User profile updated successfully", response.getBody());
        verify(userService).updateUserProfile(1, userProfileUpdateDto);
    }

    @Test
    void deleteUser_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = userController.deleteUser(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User deleted successfully", response.getBody());
        verify(userService).deleteUserById(1);
    }

    @Test
    void getAllUsers_ShouldHandleServiceException() {
        when(userService.getAllUsers()).thenThrow(new ServiceException("Failed to get users"));

        assertThrows(ServiceException.class, () -> userController.getAllUsers());
        verify(userService).getAllUsers();
    }

    @Test
    void getUserById_ShouldHandleServiceException() {
        when(userService.getUserById(anyInt()))
                .thenThrow(new ServiceException("User not found"));

        assertThrows(ServiceException.class, () -> userController.getUserById(1));
        verify(userService).getUserById(1);
    }

    @Test
    void createUser_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create user")).when(userService).addUser(any(UserRequestDto.class));

        assertThrows(ServiceException.class, () -> userController.createUser(userRequestDto));
        verify(userService).addUser(userRequestDto);
    }

    @Test
    void updateUser_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update user")).when(userService).updateUser(anyInt(), any(UserRequestDto.class));

        assertThrows(ServiceException.class, () -> userController.updateUser(1, userRequestDto));
        verify(userService).updateUser(1, userRequestDto);
    }

    @Test
    void updateUserProfile_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update user profile")).when(userService).updateUserProfile(anyInt(), any(UserProfileUpdateDto.class));

        assertThrows(ServiceException.class, () -> userController.updateUserProfile(1, userProfileUpdateDto));
        verify(userService).updateUserProfile(1, userProfileUpdateDto);
    }

    @Test
    void deleteUser_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete user")).when(userService).deleteUserById(anyInt());

        assertThrows(ServiceException.class, () -> userController.deleteUser(1));
        verify(userService).deleteUserById(1);
    }
} 