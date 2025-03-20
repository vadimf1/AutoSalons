package org.example.repository.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.example.model.Review;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ReviewSpecification {
    public Specification<Review> filter(Integer rating, LocalDate createdAt) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (rating != null) {
                predicates.add(cb.equal(root.get("rating"), rating));
            }

            if (createdAt != null) {
                predicates.add(cb.equal(root.get("createdAt"), createdAt));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
