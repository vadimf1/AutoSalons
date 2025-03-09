package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.AutoSalonDto;
import org.example.exception.ServiceException;
import org.example.mapper.AutoSalonMapper;
import org.example.repository.AutoSalonRepository;
import org.example.service.AutoSalonService;
import org.example.util.error.AutoSalonExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AutoSalonServiceImpl implements AutoSalonService {
    private final AutoSalonRepository autoSalonRepository;
    private final AutoSalonMapper autoSalonMapper;

    public void addAutoSalon(AutoSalonDto autoSalonDto) {
        if (autoSalonDto.getId() != null) {
            throw new ServiceException(AutoSalonExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }
        autoSalonRepository.save(autoSalonMapper.toEntity(autoSalonDto));
    }

    public List<AutoSalonDto> getAllAutoSalons() {
        return autoSalonRepository.findAll()
                .stream()
                .map(autoSalonMapper::toDto)
                .toList();
    }

    public AutoSalonDto getAutoSalonById(int id) {
        return autoSalonRepository.findById(id)
                .map(autoSalonMapper::toDto)
                .orElseThrow(() -> new ServiceException(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + id));
    }

    public void updateAutoSalon(AutoSalonDto autoSalonDto) {
        autoSalonRepository.save(autoSalonMapper.toEntity(autoSalonDto));
    }

    public void deleteAutoSalonById(int id) {
        autoSalonRepository.deleteById(id);
    }
}
