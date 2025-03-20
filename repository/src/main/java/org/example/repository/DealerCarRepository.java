package org.example.repository;

import org.example.model.Car;
import org.example.model.Dealer;
import org.example.model.DealerCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealerCarRepository extends JpaRepository<DealerCar, Integer> {

    List<DealerCar> findByDealer(Dealer dealer);

    List<DealerCar> findByCar(Car car);
}
