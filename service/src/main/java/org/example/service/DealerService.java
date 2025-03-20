package org.example.service;

import org.example.dto.request.DealerFilterRequest;
import org.example.dto.request.DealerRequestDto;
import org.example.dto.response.DealerResponseDto;

import java.util.List;

public interface DealerService {
    List<DealerResponseDto> getAllDealers();
    void addDealer(DealerRequestDto dealerDto);
    DealerResponseDto getDealerById(int id);
    void updateDealer(int id, DealerRequestDto dealerDto);
    void deleteDealerById(int id);
    List<DealerResponseDto> getFilteredDealers(DealerFilterRequest dealerFilterRequest);
}
