package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.ClientRepository;
import org.example.repository.DealerRepository;
import org.example.repository.ReviewRepository;
import org.example.dto.ReviewDto;
import org.example.exception.ServiceException;
import org.example.mapper.ReviewMapper;
import org.example.model.Review;
import org.example.service.ReviewService;
import org.example.util.error.AutoSalonExceptionCode;
import org.example.util.error.CarExceptionCode;
import org.example.util.error.ClientExceptionCode;
import org.example.util.error.DealerExceptionCode;
import org.example.util.error.ReviewExceptionCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final DealerRepository dealerRepository;
    private final AutoSalonRepository autoSalonRepository;
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional
    public ReviewDto getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId)
                .map(reviewMapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        ReviewExceptionCode.REVIEW_NOT_FOUND_BY_ID.getMessage() + reviewId));
    }

    @Transactional
    public List<ReviewDto> getReviewsByRating(int rating) {
        return reviewRepository.findByRating(rating)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional
    public List<ReviewDto> getReviewsByDate(LocalDate date) {
        return reviewRepository.findByCreatedAt(date)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional
    public List<ReviewDto> getReviewByClientId(int clientId) {
        return reviewRepository.findByClient_Id(clientId)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional
    public void addReview(ReviewDto reviewDto) {
        if (reviewDto.getId() != null) {
            throw new ServiceException(ReviewExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }

        long count = Stream.of(reviewDto.getCar(), reviewDto.getDealer(), reviewDto.getAutoSalon())
                .filter(Objects::nonNull)
                .count();

        if (count > 1 || count == 0) {
            throw new ServiceException(ReviewExceptionCode.INVALID_REVIEW_ASSOCIATION.getMessage());
        }

        Review review = reviewMapper.toEntity(reviewDto);

        if (reviewDto.getClient().getId() == null) {
            throw new ServiceException(ClientExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }

        review.setClient(
                clientRepository.findById(reviewDto.getClient().getId())
                        .orElseThrow(() -> new ServiceException(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + reviewDto.getClient().getId()))
        );

        if (reviewDto.getAutoSalon() != null) {
            addReviewToAutoSalon(review, reviewDto.getAutoSalon().getId());
        } else if (reviewDto.getDealer() != null) {
            addReviewToDealer(review, reviewDto.getDealer().getId());
        } else {
            addReviewToCar(review, reviewDto.getCar().getId());
        }

        reviewRepository.save(review);
    }

    public void addReviewToAutoSalon(Review review, Integer autoSalonId) {
        if (autoSalonId == null) {
            throw new ServiceException(AutoSalonExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }
        review.setAutoSalon(autoSalonRepository.findById(autoSalonId)
                .orElseThrow(() -> new ServiceException(
                        AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + autoSalonId)));
    }

    public void addReviewToDealer(Review review, Integer dealerId) {
        if (dealerId == null) {
            throw new ServiceException(DealerExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }

        review.setDealer(dealerRepository.findById(dealerId)
                .orElseThrow(() -> new ServiceException(
                        DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + dealerId)));

    }

    public void addReviewToCar(Review review, Integer carId) {
        if (carId == null) {
            throw new ServiceException(CarExceptionCode.ID_FIELD_EXPECTED_NULL.getMessage());
        }

        review.setCar(carRepository.findById(carId)
                .orElseThrow(() -> new ServiceException(
                        CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + carId)));
    }

    @Transactional
    public void updateReview(ReviewDto updatedReviewDto) {
        getReviewById(updatedReviewDto.getId());
        reviewRepository.save(reviewMapper.toEntity(updatedReviewDto));
    }

    @Transactional
    public void deleteReviewById(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
