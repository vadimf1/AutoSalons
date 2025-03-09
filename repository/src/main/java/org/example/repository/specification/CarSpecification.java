package org.example.repository.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.example.model.Car;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class CarSpecification {

    public Specification<Car> filterBy(String vin, String make, String model, Integer year) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (vin != null) {
                predicate = cb.and(predicate, cb.equal(root.get("vin"), vin));
            }

            if (make != null) {
                predicate = cb.and(predicate, cb.equal(root.get("make"), make));
            }

            if (model != null) {
                predicate = cb.and(predicate, cb.equal(root.get("model"), model));
            }

            if (year != null) {
                predicate = cb.and(predicate, cb.equal(root.get("year"), year));
            }

            return predicate;
        };
    }
}