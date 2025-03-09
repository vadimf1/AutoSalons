package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.repository.UserRepository;
import org.example.dto.UserDto;
import org.example.exception.ServiceException;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.example.util.error.UserExceptionCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void addUser(UserDto userDto) {
        if (userDto.getId() != null) {
            throw new ServiceException(UserExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }
        userRepository.save(userMapper.toEntity(userDto));
    }

    @Transactional
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional
    public UserDto getUserById(int userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + userId));
    }

    @Transactional
    public void updateUser(UserDto updatedUserDto) {
        userRepository.save(userMapper.toEntity(updatedUserDto));
    }

    @Transactional
    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(UserExceptionCode.USER_NOT_FOUND_BY_ID.getMessage() + username));
    }
}
