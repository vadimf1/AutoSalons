package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.DealerDto;
import org.example.exception.ServiceException;
import org.example.mapper.DealerMapper;
import org.example.repository.DealerRepository;
import org.example.service.DealerService;
import org.example.util.error.DealerExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DealerServiceImpl implements DealerService {
    private final DealerRepository dealerRepository;
    private final DealerMapper dealerMapper;

    public List<DealerDto> getAllDealers() {
        return dealerRepository.findAll()
                .stream()
                .map(dealerMapper::toDto)
                .toList();
    }

    public void addDealer(DealerDto dealerDto) {
        if (dealerDto.getId() != null) {
            throw new ServiceException(DealerExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }

        dealerRepository.save(dealerMapper.toEntity(dealerDto));
    }

    public DealerDto getDealerById(int id) {
        return dealerRepository.findById(id)
                .map(dealerMapper::toDto)
                .orElseThrow(() -> new ServiceException(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + id));

    }

    public void updateDealer(DealerDto dealerDto) {
        getDealerById(dealerDto.getId());
        dealerRepository.save(dealerMapper.toEntity(dealerDto));
    }

    public void deleteDealerById(int id) {
        dealerRepository.deleteById(id);
    }

    public DealerDto getDealerByName(String name) {
        return dealerRepository.findByName(name)
                .map(dealerMapper::toDto)
                .orElseThrow(() -> new ServiceException(DealerExceptionCode.DEALER_NOT_FOUND_BY_NAME.getMessage() + name));
    }
}
