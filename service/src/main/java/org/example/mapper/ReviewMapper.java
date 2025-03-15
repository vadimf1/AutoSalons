package org.example.mapper;

import org.example.dto.request.ReviewRequestDto;
import org.example.dto.response.ReviewResponseDto;
import org.example.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewResponseDto toDto(Review review);
    Review toEntity(ReviewRequestDto reviewDto);
    void updateEntityFromDto(ReviewRequestDto reviewRequestDto, @MappingTarget Review review);
}
