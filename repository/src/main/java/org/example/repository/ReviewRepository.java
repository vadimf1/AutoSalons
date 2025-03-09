package org.example.repository;

import org.example.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByRating(int rating);

    List<Review> findByCreatedAt(LocalDate createdAt);

    List<Review> findByClient_Id(Integer clientId);

    List<Review> findByCar_Id(Integer carId);

    List<Review> findByDealer_Id(Integer dealerId);

    List<Review> findByAutoSalon_Id(Integer autoSalonId);
}
