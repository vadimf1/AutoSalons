package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.SaleRequestDto;
import org.example.dto.response.SaleResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.SaleMapper;
import org.example.model.Sale;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.ClientRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.SaleRepository;
import org.example.service.SaleService;
import org.example.util.error.AutoSalonExceptionCode;
import org.example.util.error.CarExceptionCode;
import org.example.util.error.ClientExceptionCode;
import org.example.util.error.EmployeeExceptionCode;
import org.example.util.error.SaleExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final AutoSalonRepository autoSalonRepository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;

    public void addSale(SaleRequestDto saleDto) {
        Sale sale = saleMapper.toEntity(saleDto);

        addRelationsToSale(sale, saleDto);

        saleRepository.save(sale);
    }

    private void addRelationsToSale(Sale sale, SaleRequestDto saleDto) {
        sale.setAutoSalon(
                autoSalonRepository.findById(saleDto.getAutoSalonId())
                        .orElseThrow(() -> new ServiceException(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + saleDto.getAutoSalonId()))
        );

        sale.setClient(
                clientRepository.findById(saleDto.getClientId())
                        .orElseThrow(() -> new ServiceException(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + saleDto.getClientId()))
        );

        sale.setCar(
                carRepository.findById(saleDto.getCarId())
                        .orElseThrow(() -> new ServiceException(CarExceptionCode.CAR_NOT_FOUNT_BY_VIN.getMessage() + saleDto.getCarId()))
        );

        sale.setEmployee(
                employeeRepository.findById(saleDto.getEmployeeId())
                        .orElseThrow(() -> new ServiceException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND_BY_ID.getMessage() + saleDto.getEmployeeId()))
        );
    }

    public List<SaleResponseDto> getAllSales() {
        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toDto)
                .toList();
    }

    public SaleResponseDto getSaleById(int id) {
        return saleRepository.findById(id)
                .map(saleMapper::toDto)
                .orElseThrow(() -> new ServiceException(SaleExceptionCode.SALE_NOT_FOUND_BY_ID.getMessage() + id));
    }

    public void updateSale(int id, SaleRequestDto saleDto) {
        Sale sale = saleRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(SaleExceptionCode.SALE_NOT_FOUND_BY_ID.getMessage() + id));

        saleMapper.updateEntityFromDto(saleDto, sale);
        addRelationsToSale(sale, saleDto);

        saleRepository.save(sale);
    }

    public void deleteSaleById(int id) {
        saleRepository.deleteById(id);
    }

    public List<SaleResponseDto> getSaleByClientId(int clientId) {
        return saleRepository.findByClient(
                        clientRepository.findById(clientId)
                                .orElseThrow(() -> new ServiceException(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + clientId))
                )
                .stream()
                .map(saleMapper::toDto)
                .toList();
    }
}
