package org.example.service;

import org.example.dto.request.AutoSalonRequestDto;
import org.example.dto.response.AutoSalonResponseDto;

import java.util.List;

public interface AutoSalonService {
    void addAutoSalon(AutoSalonRequestDto autoSalonDto);
    List<AutoSalonResponseDto> getAllAutoSalons();
    AutoSalonResponseDto getAutoSalonById(int id);
    void updateAutoSalon(int id, AutoSalonRequestDto autoSalonDto);
    void deleteAutoSalonById(int id);
}
