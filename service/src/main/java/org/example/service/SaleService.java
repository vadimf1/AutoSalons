package org.example.service;

import org.example.dto.request.SaleRequestDto;
import org.example.dto.response.SaleResponseDto;

import java.util.List;

public interface SaleService {
    void addSale(SaleRequestDto saleDto);
    List<SaleResponseDto> getAllSales();
    SaleResponseDto getSaleById(int id);
    void updateSale(int id, SaleRequestDto saleDto);
    void deleteSaleById(int id);
}
