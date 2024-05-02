package com.aartiworkspace.restaurantlisting.services;

import com.aartiworkspace.restaurantlisting.dto.RestaurantDTO;
import com.aartiworkspace.restaurantlisting.entity.Restaurant;
import com.aartiworkspace.restaurantlisting.mapper.RestaurantMapper;
import com.aartiworkspace.restaurantlisting.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository repo;
    public List<RestaurantDTO> fetchAllRestaurant() {
        List<Restaurant> restaurants = repo.findAll();
        List<RestaurantDTO> restaurantList=  restaurants.stream().map(restaurant -> RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant)).collect(Collectors.toList());
        return restaurantList;
    }

    public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = repo.save(RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO));
        RestaurantDTO restaurantAdded = RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant);
        return restaurantAdded;
    }

    public ResponseEntity<RestaurantDTO> fetchRestaurantById(Integer id) {
        Optional<Restaurant> restaurant = repo.findById(id);
        if (restaurant.isPresent()){
             return new ResponseEntity<>(RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
