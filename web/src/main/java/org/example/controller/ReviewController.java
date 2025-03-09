package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.ReviewDto;
import org.example.service.ReviewService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/client-id/{clientId}")
    @PreAuthorize("isAuthenticated()")
    public List<ReviewDto> getReviewByClientId(@PathVariable int clientId) {
        return reviewService.getReviewByClientId(clientId);
    }

    @GetMapping("/created-at/{createdAt}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public List<ReviewDto> getReviewsByDate(@PathVariable LocalDate createdAt) {
        return reviewService.getReviewsByDate(createdAt);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ReviewDto getReviewById(@PathVariable("id") int id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("/rating/{rating}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public List<ReviewDto> getReviewsByRating(@PathVariable int rating) {
        return reviewService.getReviewsByRating(rating);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void createReview(@Valid @RequestBody ReviewDto reviewDto) {
        reviewService.addReview(reviewDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public void updateReview(@Valid @RequestBody ReviewDto reviewDto) {
        reviewService.updateReview(reviewDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReviewById(id);
    }
}
