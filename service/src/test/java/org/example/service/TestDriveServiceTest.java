package org.example.service;

import org.example.dto.request.TestDriveRequestDto;
import org.example.dto.response.TestDriveResponseDto;
import org.example.mapper.TestDriveMapper;
import org.example.model.TestDrive;
import org.example.model.AutoSalon;
import org.example.model.Car;
import org.example.model.Client;
import org.example.repository.TestDriveRepository;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.ClientRepository;
import org.example.service.impl.TestDriveServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestDriveServiceTest {

    @Mock
    private TestDriveRepository testDriveRepository;

    @Mock
    private AutoSalonRepository autoSalonRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private TestDriveMapper testDriveMapper;

    @InjectMocks
    private TestDriveServiceImpl testDriveService;

    private TestDriveRequestDto testDriveRequestDto;
    private TestDrive testDrive;
    private TestDriveResponseDto testDriveResponseDto;
    private AutoSalon autoSalon;
    private Car car;
    private Client client;

    @BeforeEach
    void setUp() {
        testDriveRequestDto = TestDriveRequestDto.builder()
                .autoSalonId(1)
                .carId(1)
                .clientId(1)
                .testDriveDate(LocalDate.now())
                .status("Scheduled")
                .build();

        autoSalon = new AutoSalon();
        car = new Car();
        client = new Client();

        testDrive = TestDrive.builder()
                .autoSalon(autoSalon)
                .car(car)
                .client(client)
                .testDriveDate(LocalDate.now())
                .status("Scheduled")
                .build();

        testDriveResponseDto = TestDriveResponseDto.builder()
                .id(1)
                .testDriveDate(LocalDate.now())
                .status("Scheduled")
                .build();
    }

    @Test
    void testAddTestDrive() {
        when(testDriveMapper.toEntity(testDriveRequestDto)).thenReturn(testDrive);
        when(autoSalonRepository.findById(testDriveRequestDto.getAutoSalonId())).thenReturn(Optional.of(autoSalon));
        when(clientRepository.findById(testDriveRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(carRepository.findById(testDriveRequestDto.getCarId())).thenReturn(Optional.of(car));
        when(testDriveRepository.save(testDrive)).thenReturn(testDrive);

        testDriveService.addTestDrive(testDriveRequestDto);

        verify(testDriveMapper).toEntity(testDriveRequestDto);
        verify(autoSalonRepository).findById(testDriveRequestDto.getAutoSalonId());
        verify(clientRepository).findById(testDriveRequestDto.getClientId());
        verify(carRepository).findById(testDriveRequestDto.getCarId());
        verify(testDriveRepository).save(testDrive);
    }

    @Test
    void testGetAllTestDrives() {
        when(testDriveRepository.findAll()).thenReturn(Collections.singletonList(testDrive));
        when(testDriveMapper.toDto(testDrive)).thenReturn(testDriveResponseDto);

        var result = testDriveService.getAllTestDrives();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testDriveResponseDto, result.get(0));
    }

    @Test
    void testGetTestDriveById() {
        when(testDriveRepository.findById(1)).thenReturn(Optional.of(testDrive));
        when(testDriveMapper.toDto(testDrive)).thenReturn(testDriveResponseDto);

        var result = testDriveService.getTestDriveById(1);

        assertNotNull(result);
        assertEquals(testDriveResponseDto, result);
    }

    @Test
    void testUpdateTestDrive() {
        when(testDriveRepository.findById(1)).thenReturn(Optional.of(testDrive));
        when(autoSalonRepository.findById(testDriveRequestDto.getAutoSalonId())).thenReturn(Optional.of(autoSalon));
        when(clientRepository.findById(testDriveRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(carRepository.findById(testDriveRequestDto.getCarId())).thenReturn(Optional.of(car));

        doNothing().when(testDriveMapper).updateEntityFromDto(testDriveRequestDto, testDrive);
        when(testDriveRepository.save(testDrive)).thenReturn(testDrive);

        testDriveService.updateTestDrive(1, testDriveRequestDto);

        verify(testDriveRepository).save(testDrive);
    }

    @Test
    void testDeleteTestDriveById() {
        when(testDriveRepository.findById(1)).thenReturn(Optional.of(testDrive));

        testDriveService.deleteTestDriveById(1);

        verify(testDriveRepository).deleteById(1);
    }

    @Test
    void testGetTestDrivesByClientId() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(testDriveRepository.findByClient(client)).thenReturn(Collections.singletonList(testDrive));
        when(testDriveMapper.toDto(testDrive)).thenReturn(testDriveResponseDto);

        var result = testDriveService.getTestDrivesByClientId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testDriveResponseDto, result.get(0));
    }

    @Test
    void testGetTestDrivesByDate() {
        when(testDriveRepository.findByTestDriveDate(LocalDate.now())).thenReturn(Collections.singletonList(testDrive));
        when(testDriveMapper.toDto(testDrive)).thenReturn(testDriveResponseDto);

        var result = testDriveService.getTestDrivesByDate(LocalDate.now());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testDriveResponseDto, result.get(0));
    }
}
