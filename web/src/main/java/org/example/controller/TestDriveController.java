package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.TestDriveDto;
import org.example.service.TestDriveService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/test-drives")
@RequiredArgsConstructor
public class TestDriveController {

    private final TestDriveService testDriveService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<TestDriveDto> getAllTestDrives() {
        return testDriveService.getAllTestDrives();
    }

    @GetMapping("/client-id/{clientId}")
    @PreAuthorize("isAuthenticated()")
    public List<TestDriveDto> getTestDrivesByClient(@PathVariable int clientId) {
        return testDriveService.getTestDrivesByClientId(clientId);
    }

    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public List<TestDriveDto> getTestDrivesByDate(@PathVariable LocalDate date) {
        return testDriveService.getTestDrivesByDate(date);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public TestDriveDto getTestDriveById(@PathVariable int id) {
        return testDriveService.getTestDriveById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void createTestDrive(@Valid @RequestBody TestDriveDto testDriveDto) {
        testDriveService.addTestDrive(testDriveDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void updateTestDrive(@Valid @RequestBody TestDriveDto testDriveDto) {
        testDriveService.updateTestDrive(testDriveDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTestDrive(@PathVariable int id) {
        testDriveService.deleteTestDriveById(id);
    }
}
