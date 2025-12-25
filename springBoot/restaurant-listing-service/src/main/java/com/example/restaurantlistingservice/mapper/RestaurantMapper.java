package com.example.restaurantlistingservice.mapper;

import org.mapstruct.Mapper;

import com.example.restaurantlistingservice.dto.RestaurantDto;
import com.example.restaurantlistingservice.entity.RestaurantEntity;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantEntity toEntity(RestaurantDto dto);
    RestaurantDto toDto(RestaurantEntity entity);
}
