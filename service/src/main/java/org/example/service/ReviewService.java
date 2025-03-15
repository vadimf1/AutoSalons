package org.example.service;

import org.example.dto.request.ReviewRequestDto;
import org.example.dto.response.ReviewResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ReviewService {
    List<ReviewResponseDto> getAllReviews();
    ReviewResponseDto getReviewById(int reviewId);
    void addReview(ReviewRequestDto reviewDto);
    List<ReviewResponseDto> getReviewsByRating(int rating);
    List<ReviewResponseDto> getReviewsByDate(LocalDate date);
    List<ReviewResponseDto> getReviewByClientId(int clientId);
    void updateReview(int id, ReviewRequestDto updatedReviewDto);
    void deleteReviewById(int reviewId);
}