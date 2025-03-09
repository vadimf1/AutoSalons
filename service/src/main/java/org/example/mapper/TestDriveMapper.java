package org.example.mapper;

import org.example.dto.TestDriveDto;
import org.example.model.TestDrive;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestDriveMapper {
    TestDriveDto toDto(TestDrive testDrive);
    TestDrive toEntity(TestDriveDto testDriveDto);
}
