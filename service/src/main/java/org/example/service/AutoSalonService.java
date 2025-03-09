package org.example.service;

import org.example.dto.AutoSalonDto;

import java.util.List;

public interface AutoSalonService {
    void addAutoSalon(AutoSalonDto autoSalonDto);
    List<AutoSalonDto> getAllAutoSalons();
    AutoSalonDto getAutoSalonById(int id);
    void updateAutoSalon(AutoSalonDto autoSalonDto);
    void deleteAutoSalonById(int id);
}
