package org.example.service;

import org.example.dto.ReviewDto;
import org.example.model.Review;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReviewService {
    List<ReviewDto> getAllReviews();
    ReviewDto getReviewById(int reviewId);
    void addReview(ReviewDto reviewDto);
    List<ReviewDto> getReviewsByRating(int rating);
    List<ReviewDto> getReviewsByDate(LocalDate date);
    List<ReviewDto> getReviewByClientId(int clientId);
    void updateReview(ReviewDto updatedReviewDto);
    void deleteReviewById(int reviewId);
}