package org.example.service;

import org.example.dto.SaleDto;

import java.util.List;

public interface SaleService {
    void addSale(SaleDto saleDto);
    List<SaleDto> getAllSales();
    SaleDto getSaleById(int id);
    void updateSale(SaleDto saleDto);
    void deleteSaleById(int id);
}
