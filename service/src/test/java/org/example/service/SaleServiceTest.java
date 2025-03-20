package org.example.service;

import org.example.dto.request.SaleRequestDto;
import org.example.dto.response.SaleResponseDto;
import org.example.mapper.SaleMapper;
import org.example.model.Sale;
import org.example.model.AutoSalon;
import org.example.model.Client;
import org.example.model.Car;
import org.example.model.Employee;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.ClientRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.SaleRepository;
import org.example.service.impl.SaleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SaleMapper saleMapper;

    @Mock
    private AutoSalonRepository autoSalonRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private SaleServiceImpl saleService;

    private SaleRequestDto saleRequestDto;
    private Sale sale;
    private SaleResponseDto saleResponseDto;
    private AutoSalon autoSalon;
    private Client client;
    private Car car;
    private Employee employee;

    @BeforeEach
    void setUp() {
        saleRequestDto = SaleRequestDto.builder()
                .autoSalonId(1)
                .clientId(2)
                .carId(3)
                .employeeId(4)
                .saleDate(LocalDate.now())
                .discount(new BigDecimal("0.1"))
                .finalPrice(new BigDecimal("25000"))
                .paymentMethod("Credit")
                .warrantyPeriod(12)
                .build();

        autoSalon = new AutoSalon();
        client = new Client();
        car = new Car();
        employee = new Employee();

        sale = Sale.builder()
                .autoSalon(autoSalon)
                .client(client)
                .car(car)
                .employee(employee)
                .saleDate(LocalDate.now())
                .discount(new BigDecimal("0.1"))
                .finalPrice(new BigDecimal("25000"))
                .paymentMethod("Credit")
                .warrantyPeriod(12)
                .build();

        saleResponseDto = SaleResponseDto.builder()
                .id(1)
                .saleDate(LocalDate.now())
                .discount(new BigDecimal("0.1"))
                .finalPrice(new BigDecimal("25000"))
                .paymentMethod("Credit")
                .warrantyPeriod(12)
                .build();
    }

    @Test
    void testAddSale() {
        when(saleMapper.toEntity(saleRequestDto)).thenReturn(sale);
        when(autoSalonRepository.findById(saleRequestDto.getAutoSalonId())).thenReturn(Optional.of(autoSalon));
        when(clientRepository.findById(saleRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(carRepository.findById(saleRequestDto.getCarId())).thenReturn(Optional.of(car));
        when(employeeRepository.findById(saleRequestDto.getEmployeeId())).thenReturn(Optional.of(employee));
        when(saleRepository.save(sale)).thenReturn(sale);

        saleService.addSale(saleRequestDto);

        verify(saleMapper).toEntity(saleRequestDto);
        verify(autoSalonRepository).findById(saleRequestDto.getAutoSalonId());
        verify(clientRepository).findById(saleRequestDto.getClientId());
        verify(carRepository).findById(saleRequestDto.getCarId());
        verify(employeeRepository).findById(saleRequestDto.getEmployeeId());
        verify(saleRepository).save(sale);
    }

    @Test
    void testGetAllSales() {
        when(saleRepository.findAll()).thenReturn(Collections.singletonList(sale));
        when(saleMapper.toDto(sale)).thenReturn(saleResponseDto);

        var result = saleService.getAllSales();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(saleResponseDto, result.get(0));
    }

    @Test
    void testGetSaleById() {
        when(saleRepository.findById(1)).thenReturn(Optional.of(sale));
        when(saleMapper.toDto(sale)).thenReturn(saleResponseDto);

        var result = saleService.getSaleById(1);

        assertNotNull(result);
        assertEquals(saleResponseDto, result);
    }

    @Test
    void testUpdateSale() {
        when(saleRepository.findById(1)).thenReturn(Optional.of(sale));
        when(autoSalonRepository.findById(saleRequestDto.getAutoSalonId())).thenReturn(Optional.of(autoSalon));
        when(clientRepository.findById(saleRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(carRepository.findById(saleRequestDto.getCarId())).thenReturn(Optional.of(car));
        when(employeeRepository.findById(saleRequestDto.getEmployeeId())).thenReturn(Optional.of(employee));

        doNothing().when(saleMapper).updateEntityFromDto(saleRequestDto, sale);
        when(saleRepository.save(sale)).thenReturn(sale);

        saleService.updateSale(1, saleRequestDto);

        verify(saleRepository).save(sale);
    }

    @Test
    void testDeleteSaleById() {
        when(saleRepository.findById(1)).thenReturn(Optional.of(sale));

        saleService.deleteSaleById(1);

        verify(saleRepository).deleteById(1);
    }

    @Test
    void testGetSaleByClientId() {
        when(clientRepository.findById(2)).thenReturn(Optional.of(client));
        when(saleRepository.findByClient(client)).thenReturn(Collections.singletonList(sale));
        when(saleMapper.toDto(sale)).thenReturn(saleResponseDto);

        var result = saleService.getSaleByClientId(2);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(saleResponseDto, result.get(0));
    }
}
