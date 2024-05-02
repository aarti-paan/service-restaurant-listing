package com.aartiworkspace.restaurantlisting.controller;

import com.aartiworkspace.restaurantlisting.dto.RestaurantDTO;
import com.aartiworkspace.restaurantlisting.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/fetchAllRestaurants")
    public ResponseEntity<List<RestaurantDTO>> fetchAllRestaurant(){

        List<RestaurantDTO> restaurantList =  restaurantService.fetchAllRestaurant();
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @PostMapping(path="/addRestaurant")
    public ResponseEntity<RestaurantDTO> saveRestaurant(@RequestBody RestaurantDTO restaurantDTO){

        RestaurantDTO restaurantAdded = restaurantService.addRestaurantInDB(restaurantDTO);
        return new ResponseEntity<>( restaurantAdded, HttpStatus.CREATED);
    }

    @GetMapping("/fetchById/{id}")
    public ResponseEntity<RestaurantDTO> fetchById(@PathVariable Integer id){
       return restaurantService.fetchRestaurantById(id);
    }

}
