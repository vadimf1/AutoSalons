package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.TestDriveRequestDto;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.ClientRepository;
import org.example.repository.TestDriveRepository;
import org.example.dto.response.TestDriveResponseDto;
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
    public List<TestDriveResponseDto> getAllTestDrives() {
        return testDriveRepository.findAll()
                .stream()
                .map(testDriveMapper::toDto)
                .toList();
    }

    @Transactional
    public TestDriveResponseDto getTestDriveById(int testDriveId) {
        return testDriveRepository.findById(testDriveId)
                .map(testDriveMapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        TestDriveExceptionCode.TEST_DRIVE_NOT_FOUNT_BY_ID.getMessage() + testDriveId));
    }

    @Transactional
    public void addTestDrive(TestDriveRequestDto testDriveDto) {
        TestDrive testDrive = testDriveMapper.toEntity(testDriveDto);

        addRelationsToTestDrive(testDrive, testDriveDto);

        testDriveRepository.save(testDrive);
    }

    private void addRelationsToTestDrive(TestDrive testDrive, TestDriveRequestDto testDriveDto) {
        testDrive.setAutoSalon(autoSalonRepository.findById(testDriveDto.getAutoSalonId())
                .orElseThrow(() -> new ServiceException(
                        AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + testDriveDto.getClientId())));

        testDrive.setClient(clientRepository.findById(testDriveDto.getClientId())
                .orElseThrow(() -> new ServiceException(
                        ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + testDriveDto.getCarId())));

        testDrive.setCar(carRepository.findById(testDriveDto.getCarId())
                .orElseThrow(() -> new ServiceException(
                        CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + testDriveDto.getCarId())));
    }

    @Transactional
    public List<TestDriveResponseDto> getTestDrivesByClientId(int clientId) {
        return testDriveRepository.findByClient_Id(clientId)
                .stream()
                .map(testDriveMapper::toDto)
                .toList();
    }

    @Transactional
    public List<TestDriveResponseDto> getTestDrivesByDate(LocalDate date) {
        return testDriveRepository.findByTestDriveDate(date)
                .stream()
                .map(testDriveMapper::toDto)
                .toList();
    }

    @Transactional
    public void updateTestDrive(int id, TestDriveRequestDto testDriveDto) {
        TestDrive testDrive = testDriveRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(TestDriveExceptionCode.TEST_DRIVE_NOT_FOUNT_BY_ID.getMessage() + id));

        testDriveMapper.updateEntityFromDto(testDriveDto, testDrive);
        addRelationsToTestDrive(testDrive, testDriveDto);

        testDriveRepository.save(testDrive);
    }

    @Transactional
    public void deleteTestDriveById(int testDriveId) {
        testDriveRepository.deleteById(testDriveId);
    }
}
