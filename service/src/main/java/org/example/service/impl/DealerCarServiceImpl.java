package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.DealerCarDto;
import org.example.exception.ServiceException;
import org.example.mapper.DealerCarMapper;
import org.example.repository.DealerCarRepository;
import org.example.service.DealerCarService;
import org.example.util.error.DealerCarExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DealerCarServiceImpl implements DealerCarService {
    private final DealerCarRepository dealerCarRepository;
    private final DealerCarMapper dealerCarMapper;

    public void addDealerCar(DealerCarDto dealerCarDto) {
        if (dealerCarDto.getId() != null) {
            throw new ServiceException(DealerCarExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }
        dealerCarRepository.save(dealerCarMapper.toEntity(dealerCarDto));
    }

    public List<DealerCarDto> getAllDealerCars() {
        return dealerCarRepository.findAll()
                .stream()
                .map(dealerCarMapper::toDto)
                .toList();
    }

    public DealerCarDto getDealerCarById(Integer id) {
        return dealerCarRepository.findById(id)
                .map(dealerCarMapper::toDto)
                .orElseThrow(() -> new ServiceException(DealerCarExceptionCode.DEALER_CAR_NOT_FOUND_BY_ID.getMessage()));
    }

    public void updateDealerCar(DealerCarDto dealerCarDto) {
        getDealerCarById(dealerCarDto.getId());
        dealerCarRepository.save(dealerCarMapper.toEntity(dealerCarDto));
    }

    public void deleteDealerCarById(Integer id) {
        dealerCarRepository.deleteById(id);
    }
}
