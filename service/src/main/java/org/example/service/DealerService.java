package org.example.service;

import org.example.dto.DealerDto;

import java.util.List;

public interface DealerService {
    List<DealerDto> getAllDealers();
    void addDealer(DealerDto dealerDto);
    DealerDto getDealerById(int id);
    void updateDealer(DealerDto dealerDto);
    void deleteDealerById(int id);
    DealerDto getDealerByName(String name);
}
