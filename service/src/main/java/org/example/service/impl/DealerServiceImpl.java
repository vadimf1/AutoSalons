package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.DealerFilterRequest;
import org.example.dto.request.DealerRequestDto;
import org.example.dto.response.DealerResponseDto;
import org.example.exception.ServiceException;
import org.example.mapper.DealerMapper;
import org.example.model.Dealer;
import org.example.repository.AddressRepository;
import org.example.repository.ContactRepository;
import org.example.repository.DealerRepository;
import org.example.repository.specification.DealerSpecification;
import org.example.service.DealerService;
import org.example.util.error.AddressExceptionCode;
import org.example.util.error.ContactExceptionCode;
import org.example.util.error.DealerExceptionCode;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DealerServiceImpl implements DealerService {
    private final DealerRepository dealerRepository;
    private final DealerMapper dealerMapper;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;

    public List<DealerResponseDto> getAllDealers() {
        return dealerRepository.findAll()
                .stream()
                .map(dealerMapper::toDto)
                .toList();
    }

    public void addDealer(DealerRequestDto dealerDto) {
        Dealer dealer = dealerMapper.toEntity(dealerDto);

        addRelationsToDealer(dealer, dealerDto.getAddressId(), dealerDto.getContactIds());

        dealerRepository.save(dealer);
    }

    private void addRelationsToDealer(Dealer dealer, int addressId, List<Integer> contactIds) {
        dealer.setAddress(
                addressRepository.findById(addressId)
                        .orElseThrow(() -> new ServiceException(AddressExceptionCode.ADDRESS_NOT_FOUNT_BY_ID.getMessage() + addressId))
        );

        dealer.setContacts(new HashSet<>());

        for (Integer contactId : contactIds) {
            if (contactId == null) {
                throw new ServiceException(ContactExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
            }

            dealer.addContact(
                    contactRepository.findById(contactId)
                            .orElseThrow(() -> new ServiceException(ContactExceptionCode.CONTACT_NOT_FOUND_BY_ID.getMessage() + contactId))
            );
        }
    }

    public DealerResponseDto getDealerById(int id) {
        return dealerRepository.findById(id)
                .map(dealerMapper::toDto)
                .orElseThrow(() -> new ServiceException(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + id));

    }

    public void updateDealer(int id, DealerRequestDto dealerDto) {
        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new ServiceException(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + id));

        dealerMapper.updateEntityFromDto(dealerDto, dealer);
        addRelationsToDealer(dealer, dealerDto.getAddressId(), dealerDto.getContactIds());

        dealerRepository.save(dealer);
    }

    @Override
    public List<DealerResponseDto> getFilteredDealers(DealerFilterRequest dealerFilterRequest) {
        return dealerRepository.findAll(DealerSpecification.filter(
                        dealerFilterRequest.getCity(),
                        dealerFilterRequest.getName()
                ))
                .stream()
                .map(dealerMapper::toDto)
                .toList();
    }

    public void deleteDealerById(int id) {
        dealerRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + id));

        dealerRepository.deleteById(id);
    }
}
