package org.example.service;

import org.example.dto.request.DealerRequestDto;
import org.example.dto.request.DealerFilterRequest;
import org.example.dto.response.DealerResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.DealerMapper;
import org.example.model.Dealer;
import org.example.model.Address;
import org.example.model.Contact;
import org.example.repository.AddressRepository;
import org.example.repository.ContactRepository;
import org.example.repository.DealerRepository;
import org.example.service.impl.DealerServiceImpl;
import org.example.util.error.AddressExceptionCode;
import org.example.util.error.ContactExceptionCode;
import org.example.util.error.DealerExceptionCode;
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
class DealerServiceTest {

    @Mock
    private DealerRepository dealerRepository;
    @Mock
    private DealerMapper dealerMapper;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private DealerServiceImpl dealerService;

    private DealerRequestDto dealerRequestDto;
    private Dealer dealer;
    private Address address;
    private Contact contact;
    private DealerResponseDto dealerResponseDto;
    private DealerFilterRequest dealerFilterRequest;

    @BeforeEach
    void setUp() {
        dealerRequestDto = DealerRequestDto.builder()
                .name("Test Dealer")
                .addressId(1)
                .contactIds(List.of(1))
                .build();

        dealer = new Dealer();
        address = new Address();
        contact = new Contact();

        dealerResponseDto = DealerResponseDto.builder()
                .id(1)
                .name("Test Dealer")
                .build();

        dealerFilterRequest = DealerFilterRequest.builder()
                .city("City1")
                .name("Dealer1")
                .build();
    }

    @Test
    void addDealer_ShouldSaveDealer() {
        when(addressRepository.findById(dealerRequestDto.getAddressId())).thenReturn(Optional.of(address));
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));
        when(dealerMapper.toEntity(dealerRequestDto)).thenReturn(dealer);

        dealerService.addDealer(dealerRequestDto);

        verify(dealerRepository, times(1)).save(dealer);
    }

    @Test
    void getAllDealers_ShouldReturnListOfDealers() {
        when(dealerRepository.findAll()).thenReturn(List.of(dealer));
        when(dealerMapper.toDto(dealer)).thenReturn(dealerResponseDto);

        List<DealerResponseDto> result = dealerService.getAllDealers();

        assertEquals(1, result.size());
        assertEquals("Test Dealer", result.get(0).getName());
    }

    @Test
    void getDealerById_ShouldReturnDealer() {
        when(dealerRepository.findById(1)).thenReturn(Optional.of(dealer));
        when(dealerMapper.toDto(dealer)).thenReturn(dealerResponseDto);

        DealerResponseDto result = dealerService.getDealerById(1);

        assertNotNull(result);
        assertEquals("Test Dealer", result.getName());
    }

    @Test
    void updateDealer_ShouldUpdateDealer() {
        when(dealerRepository.findById(1)).thenReturn(Optional.of(dealer));
        when(addressRepository.findById(dealerRequestDto.getAddressId())).thenReturn(Optional.of(address));
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));
        doNothing().when(dealerMapper).updateEntityFromDto(dealerRequestDto, dealer);

        dealerService.updateDealer(1, dealerRequestDto);

        verify(dealerRepository, times(1)).save(dealer);
    }

    @Test
    void getFilteredDealers_ShouldReturnFilteredDealers() {
        when(dealerRepository.findAll(any(Specification.class))).thenReturn(List.of(dealer));

        when(dealerMapper.toDto(dealer)).thenReturn(dealerResponseDto);

        List<DealerResponseDto> result = dealerService.getFilteredDealers(dealerFilterRequest);

        assertEquals(1, result.size());
        assertEquals("Test Dealer", result.get(0).getName());
    }

    @Test
    void deleteDealerById_ShouldDeleteDealer() {
        when(dealerRepository.findById(1)).thenReturn(Optional.of(dealer));

        dealerService.deleteDealerById(1);

        verify(dealerRepository, times(1)).deleteById(1);
    }

    @Test
    void addDealer_ShouldThrowException_WhenAddressNotFound() {
        when(dealerMapper.toEntity(dealerRequestDto)).thenReturn(dealer);
        when(addressRepository.findById(dealerRequestDto.getAddressId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerService.addDealer(dealerRequestDto));

        assertEquals(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + dealerRequestDto.getAddressId(),
                exception.getMessage());
    }

    @Test
    void addDealer_ShouldThrowException_WhenContactNotFound() {
        when(dealerMapper.toEntity(dealerRequestDto)).thenReturn(dealer);
        when(addressRepository.findById(dealerRequestDto.getAddressId())).thenReturn(Optional.of(address));
        when(contactRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerService.addDealer(dealerRequestDto));

        assertEquals(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void getDealerById_ShouldThrowException_WhenDealerNotFound() {
        when(dealerRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerService.getDealerById(1));

        assertEquals(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void updateDealer_ShouldThrowException_WhenDealerNotFound() {
        when(dealerRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerService.updateDealer(1, dealerRequestDto));

        assertEquals(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void updateDealer_ShouldThrowException_WhenAddressNotFound() {
        when(dealerRepository.findById(1)).thenReturn(Optional.of(dealer));
        when(addressRepository.findById(dealerRequestDto.getAddressId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerService.updateDealer(1, dealerRequestDto));

        assertEquals(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + dealerRequestDto.getAddressId(),
                exception.getMessage());
    }

    @Test
    void updateDealer_ShouldThrowException_WhenContactNotFound() {
        when(dealerRepository.findById(1)).thenReturn(Optional.of(dealer));
        when(addressRepository.findById(dealerRequestDto.getAddressId())).thenReturn(Optional.of(address));
        when(contactRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerService.updateDealer(1, dealerRequestDto));

        assertEquals(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void deleteDealerById_ShouldThrowException_WhenDealerNotFound() {
        when(dealerRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                dealerService.deleteDealerById(1));

        assertEquals(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + 1,
                exception.getMessage());
    }
}
