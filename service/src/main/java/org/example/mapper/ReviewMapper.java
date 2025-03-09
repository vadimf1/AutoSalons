package org.example.mapper;

import org.example.dto.ReviewDto;
import org.example.model.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto toDto(Review review);
    Review toEntity(ReviewDto reviewDto);
}
