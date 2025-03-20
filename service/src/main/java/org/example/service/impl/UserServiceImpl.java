package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.UserProfileUpdateDto;
import org.example.dto.request.UserRequestDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.dto.response.UserResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.example.util.error.UserExceptionCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void addUser(UserRequestDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new ServiceException(UserExceptionCode.USER_ALREADY_EXISTS.getMessage());
        }

        User user = userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
    }

    @Transactional
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional
    public UserResponseDto getUserById(int userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + userId));
    }

    @Transactional
    public void updateUser(int id, UserRequestDto userDto) {
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + id));

        if (!Objects.equals(user.getUsername(), userDto.getUsername()) && userRepository.existsByUsername(userDto.getUsername())) {
            throw new ServiceException(UserExceptionCode.USER_ALREADY_EXISTS.getMessage());
        }

        userMapper.updateEntityFromDto(userDto, user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
    }

    @Transactional
    public void updateUserProfile(int id, UserProfileUpdateDto userProfileDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + id));

        if (!Objects.equals(user.getUsername(), userProfileDto.getUsername()) && userRepository.existsByUsername(userProfileDto.getUsername())) {
            throw new ServiceException(UserExceptionCode.USER_ALREADY_EXISTS.getMessage());
        }

        user.setUsername(userProfileDto.getUsername());
        user.setPassword(passwordEncoder.encode(userProfileDto.getPassword()));

        userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }
}
