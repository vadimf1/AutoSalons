package org.example.service;

import org.example.dto.request.UserProfileUpdateDto;
import org.example.dto.request.UserRequestDto;
import org.example.dto.response.UserResponseDto;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.model.Role;
import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
                .username("testUser")
                .password("password123")
                .role("USER")
                .build();

        userProfileUpdateDto = UserProfileUpdateDto.builder()
                .username("updatedUser")
                .password("password456")
                .build();

        user = User.builder()
                .id(1)
                .username("testUser")
                .password("password123")
                .role(Role.CLIENT)
                .createdAt(LocalDate.now())
                .build();

        userResponseDto = UserResponseDto.builder()
                .id(1)
                .username("testUser")
                .password("password123")
                .role("USER")
                .createdAt(LocalDate.now())
                .build();
    }

    @Test
    void testAddUser() {
        when(userRepository.existsByUsername(userRequestDto.getUsername())).thenReturn(false);
        when(userMapper.toEntity(userRequestDto)).thenReturn(user);
        when(passwordEncoder.encode(userRequestDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        userService.addUser(userRequestDto);

        verify(userRepository).save(user);
        verify(userMapper).toEntity(userRequestDto);
        verify(passwordEncoder).encode(userRequestDto.getPassword());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(java.util.List.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponseDto);

        var result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userResponseDto, result.get(0));
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponseDto);

        var result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(userResponseDto, result);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(userRequestDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        userService.updateUser(1, userRequestDto);

        verify(userRepository).save(user);
        verify(passwordEncoder).encode(userRequestDto.getPassword());
    }

    @Test
    void testUpdateUserProfile() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(userProfileUpdateDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        userService.updateUserProfile(1, userProfileUpdateDto);

        verify(userRepository).save(user);
        verify(passwordEncoder).encode(userProfileUpdateDto.getPassword());
    }

    @Test
    void testDeleteUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUserById(1);

        verify(userRepository).deleteById(1);
    }
}
