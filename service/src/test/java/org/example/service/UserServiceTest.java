package org.example.service;

import org.example.dto.request.UserProfileUpdateDto;
import org.example.dto.request.UserRequestDto;
import org.example.dto.response.UserResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.model.Role;
import org.example.service.impl.UserServiceImpl;
import org.example.util.error.UserExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequestDto userRequestDto;
    private UserProfileUpdateDto userProfileUpdateDto;
    private User user;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        userRequestDto = UserRequestDto.builder()
                .username("testuser")
                .password("password123")
                .role("CLIENT")
                .build();

        user = User.builder()
                .id(1)
                .username("testuser")
                .password("encodedPassword")
                .role(Role.CLIENT)
                .createdAt(LocalDate.now())
                .build();

        userResponseDto = UserResponseDto.builder()
                .id(1)
                .username("testuser")
                .role("CLIENT")
                .createdAt(LocalDate.now())
                .build();

        userProfileUpdateDto = UserProfileUpdateDto.builder()
                .username("newusername")
                .password("newpassword")
                .build();
    }

    @Test
    void addUser_ShouldSaveUser() {
        when(userRepository.existsByUsername(userRequestDto.getUsername())).thenReturn(false);
        when(userMapper.toEntity(userRequestDto)).thenReturn(user);
        when(passwordEncoder.encode(userRequestDto.getPassword())).thenReturn("encodedPassword");

        userService.addUser(userRequestDto);

        verify(userRepository).save(user);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.toDto(user)).thenReturn(userResponseDto);

        var result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(userResponseDto, result.get(0));
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponseDto);

        var result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(userResponseDto, result);
    }

    @Test
    void updateUser_ShouldUpdateUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(userRequestDto.getPassword())).thenReturn("encodedPassword");
        doNothing().when(userMapper).updateEntityFromDto(userRequestDto, user);

        userService.updateUser(1, userRequestDto);

        verify(userRepository).save(user);
    }

    @Test
    void updateUserProfile_ShouldUpdateUserProfile() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(userProfileUpdateDto.getPassword())).thenReturn("encodedPassword");

        userService.updateUserProfile(1, userProfileUpdateDto);

        verify(userRepository).save(user);
    }

    @Test
    void deleteUserById_ShouldDeleteUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUserById(1);

        verify(userRepository).deleteById(1);
    }

    @Test
    void addUser_ShouldThrowException_WhenUserAlreadyExists() {
        when(userRepository.existsByUsername(userRequestDto.getUsername())).thenReturn(true);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                userService.addUser(userRequestDto));

        assertEquals(UserExceptionCode.USER_ALREADY_EXISTS.getMessage(),
                exception.getMessage());
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                userService.getUserById(1));

        assertEquals(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                userService.updateUser(1, userRequestDto));

        assertEquals(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void updateUser_ShouldThrowException_WhenNewUsernameAlreadyExists() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userRequestDto.setUsername("newusername");
        when(userRepository.existsByUsername(userRequestDto.getUsername())).thenReturn(true);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                userService.updateUser(1, userRequestDto));

        assertEquals(UserExceptionCode.USER_ALREADY_EXISTS.getMessage(),
                exception.getMessage());
    }

    @Test
    void updateUserProfile_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                userService.updateUserProfile(1, userProfileUpdateDto));

        assertEquals(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void deleteUserById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                userService.deleteUserById(1));

        assertEquals(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }
}
