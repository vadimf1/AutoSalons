package org.example.controller;

import org.example.dto.request.ReviewRequestDto;
import org.example.dto.response.ReviewResponseDto;
import org.example.exception.ServiceException;
import org.example.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private ReviewRequestDto reviewRequestDto;
    private ReviewResponseDto reviewResponseDto;
    private List<ReviewResponseDto> reviews;

    @BeforeEach
    void setUp() {
        reviewRequestDto = ReviewRequestDto.builder()
                .rating(5)
                .description("Great service!")
                .clientId(1)
                .carId(1)
                .build();

        reviewResponseDto = ReviewResponseDto.builder()
                .build();

        reviews = Arrays.asList(reviewResponseDto);
    }

    @Test
    void getAllReviews_ShouldReturnListOfReviews() {
        when(reviewService.getAllReviews()).thenReturn(reviews);

        ResponseEntity<List<ReviewResponseDto>> response = reviewController.getAllReviews();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(reviews, response.getBody());
        verify(reviewService).getAllReviews();
    }

    @Test
    void getReviewById_ShouldReturnReview() {
        when(reviewService.getReviewById(anyInt())).thenReturn(reviewResponseDto);

        ResponseEntity<ReviewResponseDto> response = reviewController.getReviewById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(reviewResponseDto, response.getBody());
        verify(reviewService).getReviewById(1);
    }

    @Test
    void getReviewsByClientId_ShouldReturnListOfReviews() {
        when(reviewService.getReviewsByClientId(anyInt())).thenReturn(reviews);

        ResponseEntity<List<ReviewResponseDto>> response = reviewController.getReviewsByClientId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(reviews, response.getBody());
        verify(reviewService).getReviewsByClientId(1);
    }

    @Test
    void getReviewsByCarId_ShouldReturnListOfReviews() {
        when(reviewService.getReviewsByCarId(anyInt())).thenReturn(reviews);

        ResponseEntity<List<ReviewResponseDto>> response = reviewController.getReviewsByCarId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(reviews, response.getBody());
        verify(reviewService).getReviewsByCarId(1);
    }

    @Test
    void createReview_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = reviewController.createReview(reviewRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Review added successfully", response.getBody());
        verify(reviewService).addReview(reviewRequestDto);
    }

    @Test
    void updateReview_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = reviewController.updateReview(1, reviewRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Review updated successfully", response.getBody());
        verify(reviewService).updateReview(1, reviewRequestDto);
    }

    @Test
    void deleteReview_ShouldReturnSuccessMessage() {
        ResponseEntity<String> response = reviewController.deleteReview(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Review deleted successfully", response.getBody());
        verify(reviewService).deleteReviewById(1);
    }

    @Test
    void getAllReviews_ShouldHandleServiceException() {
        when(reviewService.getAllReviews()).thenThrow(new ServiceException("Failed to get reviews"));

        assertThrows(ServiceException.class, () -> reviewController.getAllReviews());
        verify(reviewService).getAllReviews();
    }

    @Test
    void getReviewById_ShouldHandleServiceException() {
        when(reviewService.getReviewById(anyInt())).thenThrow(new ServiceException("Review not found"));

        assertThrows(ServiceException.class, () -> reviewController.getReviewById(1));
        verify(reviewService).getReviewById(1);
    }

    @Test
    void getReviewsByClientId_ShouldHandleServiceException() {
        when(reviewService.getReviewsByClientId(anyInt())).thenThrow(new ServiceException("Failed to get reviews"));

        assertThrows(ServiceException.class, () -> reviewController.getReviewsByClientId(1));
        verify(reviewService).getReviewsByClientId(1);
    }

    @Test
    void getReviewsByCarId_ShouldHandleServiceException() {
        when(reviewService.getReviewsByCarId(anyInt())).thenThrow(new ServiceException("Failed to get reviews"));

        assertThrows(ServiceException.class, () -> reviewController.getReviewsByCarId(1));
        verify(reviewService).getReviewsByCarId(1);
    }

    @Test
    void createReview_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to create review")).when(reviewService).addReview(any(ReviewRequestDto.class));

        assertThrows(ServiceException.class, () -> reviewController.createReview(reviewRequestDto));
        verify(reviewService).addReview(reviewRequestDto);
    }

    @Test
    void updateReview_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to update review")).when(reviewService).updateReview(anyInt(), any(ReviewRequestDto.class));

        assertThrows(ServiceException.class, () -> reviewController.updateReview(1, reviewRequestDto));
        verify(reviewService).updateReview(1, reviewRequestDto);
    }

    @Test
    void deleteReview_ShouldHandleServiceException() {
        doThrow(new ServiceException("Failed to delete review")).when(reviewService).deleteReviewById(anyInt());

        assertThrows(ServiceException.class, () -> reviewController.deleteReview(1));
        verify(reviewService).deleteReviewById(1);
    }
} 