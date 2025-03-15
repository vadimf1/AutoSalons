package org.example.mapper;

import org.example.dto.request.TestDriveRequestDto;
import org.example.dto.response.TestDriveResponseDto;
import org.example.model.TestDrive;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TestDriveMapper {
    TestDriveResponseDto toDto(TestDrive testDrive);
    TestDrive toEntity(TestDriveRequestDto testDriveDto);
    void updateEntityFromDto(TestDriveRequestDto testDriveRequestDto, @MappingTarget TestDrive testDrive);
}
