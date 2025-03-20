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

        addRelationsToDealerCar(dealerCar, dealerCarDto);

        dealerCarRepository.save(dealerCar);
    }

    private void addRelationsToDealerCar(DealerCar dealerCar, DealerCarRequestDto dealerCarDto) {
        dealerCar.setDealer(
                dealerRepository.findById(dealerCarDto.getDealerId())
                        .orElseThrow(() -> new ServiceException(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + dealerCarDto.getDealerId()))
        );

        dealerCar.setCar(
                carRepository.findById(dealerCarDto.getCarId())
                        .orElseThrow(() -> new ServiceException(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + dealerCarDto.getCarId()))
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
        addRelationsToDealerCar(dealerCar, dealerCarDto);

        dealerCarRepository.save(dealerCar);
    }

    public List<DealerCarResponseDto> getDealersCarByCarId(int carId) {
        return dealerCarRepository.findByCar(
                    carRepository.findById(carId)
                            .orElseThrow(() -> new ServiceException(CarExceptionCode.ID_FIELD_NOT_NULL.getMessage() + carId))
                )
                .stream()
                .map(dealerCarMapper::toDto)
                .toList();
    }

    public List<DealerCarResponseDto> getDealersCarsByDealerId(int dealerId) {
        return dealerCarRepository.findByDealer(
                        dealerRepository.findById(dealerId)
                                .orElseThrow(() -> new ServiceException(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + dealerId))
                )
                .stream()
                .map(dealerCarMapper::toDto)
                .toList();
    }

    public void deleteDealerCarById(Integer id) {
        dealerCarRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(DealerCarExceptionCode.DEALER_CAR_NOT_FOUND_BY_ID.getMessage() + id));

        dealerCarRepository.deleteById(id);
    }
}
