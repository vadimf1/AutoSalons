package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.DealerCarRequestDto;
import org.example.dto.response.DealerCarResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.DealerCarMapper;
import org.example.model.DealerCar;
import org.example.repository.CarRepository;
import org.example.repository.DealerCarRepository;
import org.example.repository.DealerRepository;
import org.example.service.DealerCarService;
import org.example.util.error.CarExceptionCode;
import org.example.util.error.DealerCarExceptionCode;
import org.example.util.error.DealerExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DealerCarServiceImpl implements DealerCarService {
    private final DealerCarRepository dealerCarRepository;
    private final DealerRepository dealerRepository;
    private final CarRepository carRepository;
    private final DealerCarMapper dealerCarMapper;

    public void addDealerCar(DealerCarRequestDto dealerCarDto) {
        DealerCar dealerCar = dealerCarMapper.toEntity(dealerCarDto);

        addRelationsToDealerCar(dealerCar, dealerCarDto.getDealerId(), dealerCarDto.getCarId());

        dealerCarRepository.save(dealerCar);
    }

    private void addRelationsToDealerCar(DealerCar dealerCar, int dealerId, int carId) {
        dealerCar.setDealer(
                dealerRepository.findById(dealerId)
                        .orElseThrow(() -> new ServiceException(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + dealerId))
        );

        dealerCar.setCar(
                carRepository.findById(carId)
                        .orElseThrow(() -> new ServiceException(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + carId))
        );
    }

    public List<DealerCarResponseDto> getAllDealerCars() {
        return dealerCarRepository.findAll()
                .stream()
                .map(dealerCarMapper::toDto)
                .toList();
    }

    public DealerCarResponseDto getDealerCarById(Integer id) {
        return dealerCarRepository.findById(id)
                .map(dealerCarMapper::toDto)
                .orElseThrow(() -> new ServiceException(DealerCarExceptionCode.DEALER_CAR_NOT_FOUND_BY_ID.getMessage() + id));
    }

    public void updateDealerCar(int id, DealerCarRequestDto dealerCarDto) {
        DealerCar dealerCar = dealerCarRepository.findById(id)
                .orElseThrow(() -> new ServiceException(DealerCarExceptionCode.DEALER_CAR_NOT_FOUND_BY_ID.getMessage() + id));

        dealerCarMapper.updateEntityFromDto(dealerCarDto, dealerCar);
        addRelationsToDealerCar(dealerCar, dealerCarDto.getDealerId(), dealerCarDto.getCarId());

        dealerCarRepository.save(dealerCar);
    }

    public void deleteDealerCarById(Integer id) {
        dealerCarRepository.deleteById(id);
    }
}
