package org.example.mapper;

import org.example.dto.request.UserRequestDto;
import org.example.model.User;
import org.example.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toDto(User user);
    User toEntity(UserRequestDto userDto);
    void updateEntityFromDto(UserRequestDto userRequestDto, @MappingTarget User user);
}

