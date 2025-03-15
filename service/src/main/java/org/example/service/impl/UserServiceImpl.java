package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.UserRequestDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.dto.response.UserResponseDto;
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
    public void addUser(UserRequestDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
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

        userMapper.updateEntityFromDto(userDto, user);

        userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(UserExceptionCode.USER_NOT_FOUND_BY_USERNAME.getMessage() + username));
    }
}
