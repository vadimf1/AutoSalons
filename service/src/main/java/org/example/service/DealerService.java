package org.example.service;

import org.example.dto.response.DealerResponseDto;

import java.util.List;

public interface DealerService {
    List<DealerResponseDto> getAllDealers();
    void addDealer(DealerResponseDto dealerDto);
    DealerResponseDto getDealerById(int id);
    void updateDealer(DealerResponseDto dealerDto);
    void deleteDealerById(int id);
    DealerResponseDto getDealerByName(String name);
}
