package org.example.service;

import org.example.dto.request.TestDriveRequestDto;
import org.example.dto.response.TestDriveResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface TestDriveService {
    List<TestDriveResponseDto> getAllTestDrives();
    TestDriveResponseDto getTestDriveById(int id);
    void addTestDrive(TestDriveRequestDto testDriveDto);
    List<TestDriveResponseDto> getTestDrivesByClientId(int clientId);
    List<TestDriveResponseDto> getTestDrivesByDate(LocalDate date);
    void updateTestDrive(int id, TestDriveRequestDto testDriveDto);
    void deleteTestDriveById(int testDriveId);
}
