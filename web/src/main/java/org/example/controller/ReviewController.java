package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.ReviewFilterRequest;
import org.example.dto.request.ReviewRequestDto;
import org.example.dto.response.ReviewResponseDto;
import org.example.service.ReviewService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable("id") int id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByClientId(@PathVariable int clientId) {
        return ResponseEntity.ok(reviewService.getReviewsByClientId(clientId));
    }

    @GetMapping("/car/{carId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByCarId(@PathVariable int carId) {
        return ResponseEntity.ok(reviewService.getReviewsByCarId(carId));
    }

    @GetMapping("/auto-salon/{autoSalonId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByAutoSalonId(@PathVariable int autoSalonId) {
        return ResponseEntity.ok(reviewService.getReviewsByAutoSalonId(autoSalonId));
    }

    @GetMapping("/dealer/{dealerId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByDealerId(@PathVariable int dealerId) {
        return ResponseEntity.ok(reviewService.getReviewsByDealerId(dealerId));
    }

    @PostMapping("/filter")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReviewResponseDto>> getFilteredReviews(@RequestBody ReviewFilterRequest reviewFilterRequest) {
        return ResponseEntity.ok(reviewService.getFilteredReviews(reviewFilterRequest));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> createReview(@Valid @RequestBody ReviewRequestDto reviewDto) {
        reviewService.addReview(reviewDto);
        return ResponseEntity.ok("Review added successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> updateReview(@PathVariable int id, @Valid @RequestBody ReviewRequestDto reviewDto) {
        reviewService.updateReview(id, reviewDto);
        return ResponseEntity.ok("Review updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteReview(@PathVariable int id) {
        reviewService.deleteReviewById(id);
        return ResponseEntity.ok("Review deleted successfully");
    }
}
