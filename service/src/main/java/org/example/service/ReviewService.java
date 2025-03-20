package org.example.service;

import org.example.dto.request.ReviewFilterRequest;
import org.example.dto.request.ReviewRequestDto;
import org.example.dto.response.ReviewResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ReviewService {
    List<ReviewResponseDto> getAllReviews();
    ReviewResponseDto getReviewById(int reviewId);
    void addReview(ReviewRequestDto reviewDto);
    List<ReviewResponseDto> getReviewByClientId(int clientId);
    void updateReview(int id, ReviewRequestDto updatedReviewDto);
    void deleteReviewById(int reviewId);
    List<ReviewResponseDto> getReviewByCarId(int carId);
    List<ReviewResponseDto> getReviewByAutoSalonId(int autoSalonId);
    List<ReviewResponseDto> getReviewByDealerId(int dealerId);
    List<ReviewResponseDto> getFilteredReviews(ReviewFilterRequest reviewFilterRequest);
}