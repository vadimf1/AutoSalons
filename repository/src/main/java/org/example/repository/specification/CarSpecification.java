package org.example.repository.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.example.model.Car;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CarSpecification {

    public Specification<Car> filterBy(String vin, String make, String model, Integer year) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (vin != null) {
                predicates.add(cb.equal(root.get("vin"), vin));
            }
            if (make != null) {
                predicates.add(cb.equal(root.get("make"), make));
            }
            if (model != null) {
                predicates.add(cb.equal(root.get("model"), model));
            }
            if (year != null) {
                predicates.add(cb.equal(root.get("year"), year));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}