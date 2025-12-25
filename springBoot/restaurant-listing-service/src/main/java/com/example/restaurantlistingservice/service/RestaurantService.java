package com.example.restaurantlistingservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restaurantlistingservice.dto.RestaurantDto;
import com.example.restaurantlistingservice.entity.RestaurantEntity;
import com.example.restaurantlistingservice.mapper.RestaurantMapper;
import com.example.restaurantlistingservice.respository.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantMapper restaurantMapper;

    public List<RestaurantDto> getAllRestaurants() {
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(restaurantMapper::toDto).toList();
    }

    public RestaurantDto getRestaurantById(Integer id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElse(null);
        return restaurantMapper.toDto(restaurant);
    }

    public RestaurantDto addRestaurant(RestaurantDto restaurantDto) {
        RestaurantEntity restaurantEntity = restaurantMapper.toEntity(restaurantDto);
        RestaurantEntity savedEntity = restaurantRepository.save(restaurantEntity);
        return restaurantMapper.toDto(savedEntity);
    }

}
