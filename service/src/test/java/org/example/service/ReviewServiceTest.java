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
import org.example.util.error.AutoSalonExceptionCode;
import org.example.util.error.CarExceptionCode;
import org.example.util.error.ClientExceptionCode;
import org.example.util.error.DealerExceptionCode;
import org.example.util.error.ReviewExceptionCode;
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
        review = new Review();

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

        var result = reviewService.getReviewsByClientId(1);

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

    @Test
    void addReview_ShouldThrowException_WhenClientNotFound() {
        when(reviewMapper.toEntity(reviewRequestDto)).thenReturn(review);
        when(clientRepository.findById(reviewRequestDto.getClientId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.addReview(reviewRequestDto));

        assertEquals(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + reviewRequestDto.getClientId(),
                exception.getMessage());
    }

    @Test
    void addReview_ShouldThrowException_WhenCarNotFound() {
        reviewRequestDto.setCarId(1);
        reviewRequestDto.setDealerId(null);
        reviewRequestDto.setAutoSalonId(null);

        when(reviewMapper.toEntity(reviewRequestDto)).thenReturn(review);
        when(clientRepository.findById(reviewRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(carRepository.findById(reviewRequestDto.getCarId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.addReview(reviewRequestDto));

        assertEquals(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + reviewRequestDto.getCarId(),
                exception.getMessage());
    }

    @Test
    void addReview_ShouldThrowException_WhenAutoSalonNotFound() {
        when(reviewMapper.toEntity(reviewRequestDto)).thenReturn(review);
        when(clientRepository.findById(reviewRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(autoSalonRepository.findById(reviewRequestDto.getAutoSalonId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.addReview(reviewRequestDto));

        assertEquals(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + reviewRequestDto.getAutoSalonId(),
                exception.getMessage());
    }

    @Test
    void addReview_ShouldThrowException_WhenDealerNotFound() {
        reviewRequestDto.setCarId(null);
        reviewRequestDto.setDealerId(1);
        reviewRequestDto.setAutoSalonId(null);

        when(reviewMapper.toEntity(reviewRequestDto)).thenReturn(review);
        when(clientRepository.findById(reviewRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(dealerRepository.findById(reviewRequestDto.getDealerId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.addReview(reviewRequestDto));

        assertEquals(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + reviewRequestDto.getDealerId(),
                exception.getMessage());
    }

    @Test
    void getReviewById_ShouldThrowException_WhenReviewNotFound() {
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.getReviewById(1));

        assertEquals(ReviewExceptionCode.REVIEW_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void updateReview_ShouldThrowException_WhenReviewNotFound() {
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.updateReview(1, reviewRequestDto));

        assertEquals(ReviewExceptionCode.REVIEW_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }

    @Test
    void updateReview_ShouldThrowException_WhenClientNotFound() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        when(clientRepository.findById(reviewRequestDto.getClientId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.updateReview(1, reviewRequestDto));

        assertEquals(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + reviewRequestDto.getClientId(),
                exception.getMessage());
    }

    @Test
    void updateReview_ShouldThrowException_WhenCarNotFound() {
        reviewRequestDto.setCarId(1);
        reviewRequestDto.setDealerId(null);
        reviewRequestDto.setAutoSalonId(null);

        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        when(clientRepository.findById(reviewRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(carRepository.findById(reviewRequestDto.getCarId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.updateReview(1, reviewRequestDto));

        assertEquals(CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + reviewRequestDto.getCarId(),
                exception.getMessage());
    }

    @Test
    void updateReview_ShouldThrowException_WhenAutoSalonNotFound() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        when(clientRepository.findById(reviewRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(autoSalonRepository.findById(reviewRequestDto.getAutoSalonId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.updateReview(1, reviewRequestDto));

        assertEquals(AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + reviewRequestDto.getAutoSalonId(),
                exception.getMessage());
    }

    @Test
    void updateReview_ShouldThrowException_WhenDealerNotFound() {
        reviewRequestDto.setCarId(null);
        reviewRequestDto.setDealerId(1);
        reviewRequestDto.setAutoSalonId(null);

        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        when(clientRepository.findById(reviewRequestDto.getClientId())).thenReturn(Optional.of(client));
        when(dealerRepository.findById(reviewRequestDto.getDealerId())).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.updateReview(1, reviewRequestDto));

        assertEquals(DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + reviewRequestDto.getDealerId(),
                exception.getMessage());
    }

    @Test
    void deleteReviewById_ShouldThrowException_WhenReviewNotFound() {
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () ->
                reviewService.deleteReviewById(1));

        assertEquals(ReviewExceptionCode.REVIEW_NOT_FOUND_BY_ID.getMessage() + 1,
                exception.getMessage());
    }
}
