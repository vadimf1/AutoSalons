package org.example.repository;

import org.example.model.AutoSalon;
import org.example.model.Car;
import org.example.model.Client;
import org.example.model.Dealer;
import org.example.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review> {
    List<Review> findByClient(Client client);

    List<Review> findByCar(Car car);

    List<Review> findByAutoSalon(AutoSalon autoSalon);

    List<Review> findByDealer(Dealer dealer);
}
