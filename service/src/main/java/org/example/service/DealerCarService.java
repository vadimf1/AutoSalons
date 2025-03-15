package org.example.service;

import org.example.dto.request.DealerCarRequestDto;
import org.example.dto.response.DealerCarResponseDto;

import java.util.List;

public interface DealerCarService {
    void addDealerCar(DealerCarRequestDto dealerCarDto);
    List<DealerCarResponseDto> getAllDealerCars();
    DealerCarResponseDto getDealerCarById(Integer id);
    void updateDealerCar(int id, DealerCarRequestDto dealerCarDto);
    void deleteDealerCarById(Integer id);
}
