package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.ClientRepository;
import org.example.repository.TestDriveRepository;
import org.example.dto.TestDriveDto;
import org.example.exception.ServiceException;
import org.example.mapper.TestDriveMapper;
import org.example.model.TestDrive;
import org.example.service.TestDriveService;
import org.example.util.error.AutoSalonExceptionCode;
import org.example.util.error.CarExceptionCode;
import org.example.util.error.ClientExceptionCode;
import org.example.util.error.TestDriveExceptionCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestDriveServiceImpl implements TestDriveService {
    private final TestDriveRepository testDriveRepository;
    private final AutoSalonRepository autoSalonRepository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final TestDriveMapper testDriveMapper;

    @Transactional
    public List<TestDriveDto> getAllTestDrives() {
        return testDriveRepository.findAll()
                .stream()
                .map(testDriveMapper::toDto)
                .toList();
    }

    @Transactional
    public TestDriveDto getTestDriveById(int testDriveId) {
        return testDriveRepository.findById(testDriveId)
                .map(testDriveMapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        TestDriveExceptionCode.TEST_DRIVE_NOT_FOUNT_BY_ID.getMessage() + testDriveId));
    }

    @Transactional
    public void addTestDrive(TestDriveDto testDriveDto) {
        if (testDriveDto.getId() != null) {
            throw new ServiceException(TestDriveExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }

        TestDrive testDrive = testDriveMapper.toEntity(testDriveDto);

        if (testDriveDto.getAutoSalon().getId() == null) {
            throw new ServiceException(TestDriveExceptionCode.AUTO_SALON_ID_REQUIRED.getMessage());
        }

        testDrive.setAutoSalon(autoSalonRepository.findById(testDriveDto.getAutoSalon().getId())
                .orElseThrow(() -> new ServiceException(
                        AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + testDriveDto.getClient().getId())));

        if (testDriveDto.getClient().getId() == null) {
            throw new ServiceException(TestDriveExceptionCode.CLIENT_ID_REQUIRED.getMessage());
        }

        testDrive.setClient(clientRepository.findById(testDriveDto.getClient().getId())
                .orElseThrow(() -> new ServiceException(
                        ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + testDriveDto.getCar().getId())));

        if (testDriveDto.getCar().getId() == null) {
            throw new ServiceException(TestDriveExceptionCode.CAR_ID_REQUIRED.getMessage());
        }

        testDrive.setCar(carRepository.findById(testDriveDto.getCar().getId())
                .orElseThrow(() -> new ServiceException(
                        CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + testDriveDto.getCar().getId())));

        testDriveRepository.save(testDrive);
    }

    @Transactional
    public List<TestDriveDto> getTestDrivesByClientId(int clientId) {
        return testDriveRepository.findByClient_Id(clientId)
                .stream()
                .map(testDriveMapper::toDto)
                .toList();
    }

    @Transactional
    public List<TestDriveDto> getTestDrivesByDate(LocalDate date) {
        return testDriveRepository.findByTestDriveDate(date)
                .stream()
                .map(testDriveMapper::toDto)
                .toList();
    }

    @Transactional
    public void updateTestDrive(TestDriveDto updatedTestDriveDto) {
        testDriveRepository.save(testDriveMapper.toEntity(updatedTestDriveDto));
    }

    @Transactional
    public void deleteTestDriveById(int testDriveId) {
        testDriveRepository.deleteById(testDriveId);
    }
}
