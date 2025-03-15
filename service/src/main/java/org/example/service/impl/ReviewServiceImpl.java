package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.request.ReviewRequestDto;
import org.example.repository.AutoSalonRepository;
import org.example.repository.CarRepository;
import org.example.repository.ClientRepository;
import org.example.repository.DealerRepository;
import org.example.repository.ReviewRepository;
import org.example.dto.response.ReviewResponseDto;
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
    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional
    public ReviewResponseDto getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId)
                .map(reviewMapper::toDto)
                .orElseThrow(() -> new ServiceException(
                        ReviewExceptionCode.REVIEW_NOT_FOUND_BY_ID.getMessage() + reviewId));
    }

    @Transactional
    public List<ReviewResponseDto> getReviewsByRating(int rating) {
        return reviewRepository.findByRating(rating)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional
    public List<ReviewResponseDto> getReviewsByDate(LocalDate date) {
        return reviewRepository.findByCreatedAt(date)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional
    public List<ReviewResponseDto> getReviewByClientId(int clientId) {
        return reviewRepository.findByClient_Id(clientId)
                .stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    @Transactional
    public void addReview(ReviewRequestDto reviewDto) {

        Review review = reviewMapper.toEntity(reviewDto);

        addRelationsToReview(review, reviewDto);

        reviewRepository.save(review);
    }

    private void addRelationsToReview(Review review, ReviewRequestDto reviewDto) {
        long count = Stream.of(reviewDto.getCarId(), reviewDto.getDealerId(), reviewDto.getAutoSalonId())
                .filter(Objects::nonNull)
                .count();

        if (count > 1 || count == 0) {
            throw new ServiceException(ReviewExceptionCode.INVALID_REVIEW_ASSOCIATION.getMessage());
        }

        review.setClient(
                clientRepository.findById(reviewDto.getClientId())
                        .orElseThrow(() -> new ServiceException(ClientExceptionCode.CLIENT_NOT_FOUND_BY_ID.getMessage() + reviewDto.getClientId()))
        );

        if (reviewDto.getAutoSalonId() != null) {
            review.setAutoSalon(autoSalonRepository.findById(reviewDto.getAutoSalonId())
                    .orElseThrow(() -> new ServiceException(
                            AutoSalonExceptionCode.AUTO_SALON_NOT_FOUNT_BY_ID.getMessage() + reviewDto.getAutoSalonId())));
        } else if (reviewDto.getDealerId() != null) {
            review.setDealer(dealerRepository.findById(reviewDto.getDealerId())
                    .orElseThrow(() -> new ServiceException(
                            DealerExceptionCode.DEALER_NOT_FOUNT_BY_ID.getMessage() + reviewDto.getDealerId())));
        } else {
            review.setCar(carRepository.findById(reviewDto.getCarId())
                    .orElseThrow(() -> new ServiceException(
                            CarExceptionCode.CAR_NOT_FOUNT_BY_ID.getMessage() + reviewDto.getCarId())));
        }
    }

    @Transactional
    public void updateReview(int id, ReviewRequestDto reviewDto) {
        Review review = reviewRepository.findById(id)
                        .orElseThrow(() -> new ServiceException(ReviewExceptionCode.REVIEW_NOT_FOUND_BY_ID.getMessage() + id));

        reviewMapper.updateEntityFromDto(reviewDto, review);
        addRelationsToReview(review, reviewDto);

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReviewById(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
