package org.example.service;

import org.example.dto.request.AutoSalonFilterRequest;
import org.example.dto.request.AutoSalonRequestDto;
import org.example.dto.response.AutoSalonResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.AutoSalonMapper;
import org.example.model.AutoSalon;
import org.example.model.Contact;
import org.example.model.Address;
import org.example.repository.AutoSalonRepository;
import org.example.repository.ContactRepository;
import org.example.repository.AddressRepository;
import org.example.service.impl.AutoSalonServiceImpl;
import org.example.util.error.AutoSalonExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutoSalonServiceTest {

    @Mock
    private AutoSalonRepository autoSalonRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private AutoSalonMapper autoSalonMapper;

    @InjectMocks
    private AutoSalonServiceImpl autoSalonService;

    private AutoSalon autoSalon;
    private AutoSalonRequestDto autoSalonRequestDto;
    private AutoSalonResponseDto autoSalonResponseDto;
    private AutoSalonFilterRequest autoSalonFilterRequest;
    private Address address;
    private Contact contact;

    @BeforeEach
    void setUp() {
        autoSalon = new AutoSalon();
        autoSalonRequestDto = AutoSalonRequestDto.builder()
                .name("AutoSalon Name")
                .addressId(1)
                .contactIds(List.of(1))
                .build();
        autoSalonResponseDto = AutoSalonResponseDto.builder()
                .id(1)
                .name("AutoSalon Name")
                .build();
        autoSalonFilterRequest = AutoSalonFilterRequest.builder()
                .name("AutoSalon Name")
                .city("City")
                .build();
        address = new Address();
        contact = new Contact();
    }

    @Test
    void addAutoSalon_ShouldSaveAutoSalon() {
        when(addressRepository.findById(autoSalonRequestDto.getAddressId())).thenReturn(Optional.of(address));
        when(contactRepository.findById(autoSalonRequestDto.getContactIds().get(0))).thenReturn(Optional.of(contact));
        when(autoSalonMapper.toEntity(autoSalonRequestDto)).thenReturn(autoSalon);

        autoSalonService.addAutoSalon(autoSalonRequestDto);

        verify(autoSalonRepository, times(1)).save(autoSalon);
    }

    @Test
    void getAllAutoSalons_ShouldReturnListOfAutoSalons() {
        when(autoSalonRepository.findAll()).thenReturn(List.of(autoSalon));
        when(autoSalonMapper.toDto(autoSalon)).thenReturn(autoSalonResponseDto);

        List<AutoSalonResponseDto> result = autoSalonService.getAllAutoSalons();

        assertEquals(1, result.size());
        assertEquals("AutoSalon Name", result.get(0).getName());
    }

    @Test
    void getAutoSalonById_ShouldReturnAutoSalon() {
        when(autoSalonRepository.findById(1)).thenReturn(Optional.of(autoSalon));
        when(autoSalonMapper.toDto(autoSalon)).thenReturn(autoSalonResponseDto);

        AutoSalonResponseDto result = autoSalonService.getAutoSalonById(1);

        assertNotNull(result);
        assertEquals("AutoSalon Name", result.getName());
    }

    @Test
    void getAutoSalonById_ShouldThrowException_WhenNotFound() {
        when(autoSalonRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> autoSalonService.getAutoSalonById(1));

        assertEquals(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + 1, exception.getMessage());
    }

    @Test
    void updateAutoSalon_ShouldUpdateAutoSalon() {
        when(autoSalonRepository.findById(1)).thenReturn(Optional.of(autoSalon));
        doNothing().when(autoSalonMapper).updateEntityFromDto(autoSalonRequestDto, autoSalon);
        when(addressRepository.findById(autoSalonRequestDto.getAddressId())).thenReturn(Optional.of(address));
        when(contactRepository.findById(autoSalonRequestDto.getContactIds().get(0))).thenReturn(Optional.of(contact));

        autoSalonService.updateAutoSalon(1, autoSalonRequestDto);

        verify(autoSalonRepository, times(1)).save(autoSalon);
    }

    @Test
    void deleteAutoSalonById_ShouldDeleteAutoSalon() {
        doNothing().when(autoSalonRepository).deleteById(1);

        autoSalonService.deleteAutoSalonById(1);

        verify(autoSalonRepository, times(1)).deleteById(1);
    }

    @Test
    void getFilteredAutoSalons_ShouldReturnFilteredAutoSalons() {
        when(autoSalonRepository.findAll(any(Specification.class))).thenReturn(List.of(autoSalon));

        when(autoSalonMapper.toDto(autoSalon)).thenReturn(autoSalonResponseDto);

        List<AutoSalonResponseDto> result = autoSalonService.getFilteredAutoSalons(autoSalonFilterRequest);

        assertEquals(1, result.size());
        assertEquals("AutoSalon Name", result.get(0).getName());
    }
}
