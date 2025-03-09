package org.example.service;

import org.example.dto.DealerCarDto;

import java.util.List;

public interface DealerCarService {
    void addDealerCar(DealerCarDto dealerCarDto);
    List<DealerCarDto> getAllDealerCars();
    DealerCarDto getDealerCarById(Integer id);
    void updateDealerCar(DealerCarDto dealerCarDto);
    void deleteDealerCarById(Integer id);
}
