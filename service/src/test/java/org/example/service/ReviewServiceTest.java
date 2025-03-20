package org.example.service;

import org.example.dto.request.ReviewFilterRequest;
import org.example.dto.request.ReviewRequestDto;
import org.example.dto.response.ReviewResponseDto;
import org.example.mapper.ReviewMapper;
import org.example.model.Review;
import org.example.model.Client;
import org.example.model.Car;
import org.example.model.AutoSalon;
import org.example.model.Dealer;
import org.example.repository.ReviewRepository;
import org.example.repository.DealerRepository;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.ClientRepository;
import org.example.service.impl.ReviewServiceImpl;
import org.example.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private DealerRepository dealerRepository;

    @Mock
    private AutoSalonRepository autoSalonRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ReviewRequestDto reviewRequestDto;
    private Review review;
    private ReviewResponseDto reviewResponseDto;
    private Client client;
    private Car car;
    private AutoSalon autoSalon;
    private Dealer dealer;

    @BeforeEach
    void setUp() {
        reviewRequestDto = ReviewRequestDto.builder()
                .clientId(1)
                .description("Great experience!")
                .rating(5)
                .autoSalonId(2)
                .createdAt(LocalDate.now())
                .build();

        client = new Client();
        car = new Car();
        autoSalon = new AutoSalon();
        dealer = new Dealer();

        review = Review.builder()
                .client(client)
                .description("Great experience!")
                .rating(5)
                .autoSalon(autoSalon)
                .createdAt(LocalDate.now())
                .build();

        reviewResponseDto = ReviewResponseDto.builder()
                .id(1)
                .description("Great experience!")
                .rating(5)
                .createdAt(LocalDate.now())
                .build();
    }

    @Test
    void testAddReview() {
        when(reviewMapper.toEntity(reviewRequestDto)).thenReturn(review);
        when(clientRepository.findById(reviewRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(autoSalonRepository.findById(reviewRequestDto.getAutoSalonId())).thenReturn(Optional.of(autoSalon));
        when(reviewRepository.save(review)).thenReturn(review);

        reviewService.addReview(reviewRequestDto);

        verify(reviewMapper).toEntity(reviewRequestDto);
        verify(clientRepository).findById(reviewRequestDto.getClientId());
        verify(autoSalonRepository).findById(reviewRequestDto.getAutoSalonId());
        verify(reviewRepository).save(review);
    }

    @Test
    void testGetAllReviews() {
        when(reviewRepository.findAll()).thenReturn(Collections.singletonList(review));
        when(reviewMapper.toDto(review)).thenReturn(reviewResponseDto);

        var result = reviewService.getAllReviews();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reviewResponseDto, result.get(0));
    }

    @Test
    void testGetReviewById() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        when(reviewMapper.toDto(review)).thenReturn(reviewResponseDto);

        var result = reviewService.getReviewById(1);

        assertNotNull(result);
        assertEquals(reviewResponseDto, result);
    }

    @Test
    void testUpdateReview() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        when(clientRepository.findById(reviewRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(autoSalonRepository.findById(reviewRequestDto.getAutoSalonId())).thenReturn(Optional.of(autoSalon));

        doNothing().when(reviewMapper).updateEntityFromDto(reviewRequestDto, review);
        when(reviewRepository.save(review)).thenReturn(review);

        reviewService.updateReview(1, reviewRequestDto);

        verify(reviewRepository).save(review);
    }

    @Test
    void testDeleteReviewById() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        reviewService.deleteReviewById(1);

        verify(reviewRepository).deleteById(1);
    }

    @Test
    void testGetReviewByClientId() {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(reviewRepository.findByClient(client)).thenReturn(Collections.singletonList(review));
        when(reviewMapper.toDto(review)).thenReturn(reviewResponseDto);

        var result = reviewService.getReviewByClientId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reviewResponseDto, result.get(0));
    }

    @Test
    void testGetFilteredReviews() {
        ReviewFilterRequest filterRequest = ReviewFilterRequest.builder()
                .rating(5)
                .createdAt(LocalDate.now())
                .build();

        when(reviewRepository.findAll(any(Specification.class))).thenReturn(List.of(review));
        when(reviewMapper.toDto(review)).thenReturn(reviewResponseDto);

        var result = reviewService.getFilteredReviews(filterRequest);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reviewResponseDto, result.get(0));
    }

    @Test
    void testAddReview_InvalidAssociation() {
        reviewRequestDto.setDealerId(1);
        reviewRequestDto.setCarId(2);

        assertThrows(ServiceException.class, () -> reviewService.addReview(reviewRequestDto));
    }
}
