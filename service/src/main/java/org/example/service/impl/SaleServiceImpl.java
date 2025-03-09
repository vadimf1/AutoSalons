package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.SaleDto;
import org.example.exception.ServiceException;
import org.example.mapper.SaleMapper;
import org.example.repository.SaleRepository;
import org.example.service.SaleService;
import org.example.util.error.SaleExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    public void addSale(SaleDto saleDto) {
        if (saleDto.getId() != null) {
            throw new ServiceException(SaleExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }
        saleRepository.save(saleMapper.toEntity(saleDto));
    }

    public List<SaleDto> getAllSales() {
        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toDto)
                .toList();
    }

    public SaleDto getSaleById(int id) {
        return saleRepository.findById(id)
                .map(saleMapper::toDto)
                .orElseThrow(() -> new ServiceException(SaleExceptionCode.SALE_NOT_FOUND_BY_ID.getMessage() + id));
    }

    public void updateSale(SaleDto saleDto) {
        saleRepository.save(saleMapper.toEntity(saleDto));
    }

    public void deleteSaleById(int id) {
        saleRepository.deleteById(id);
    }
}
