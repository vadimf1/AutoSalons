package org.example.service;

import org.example.dto.TestDriveDto;

import java.time.LocalDate;
import java.util.List;

public interface TestDriveService {
    List<TestDriveDto> getAllTestDrives();
    TestDriveDto getTestDriveById(int id);
    void addTestDrive(TestDriveDto testDriveDto);
    List<TestDriveDto> getTestDrivesByClientId(int clientId);
    List<TestDriveDto> getTestDrivesByDate(LocalDate date);
    void updateTestDrive(TestDriveDto testDriveDto);
    void deleteTestDriveById(int testDriveId);
}
