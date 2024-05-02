package com.aartiworkspace.restaurantlisting.controller;

import com.aartiworkspace.restaurantlisting.dto.RestaurantDTO;
import com.aartiworkspace.restaurantlisting.services.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestaurantControllerTest {

    @InjectMocks
    RestaurantController restaurantController;

    @Mock
    RestaurantService restaurantService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFetchAllRestaurant(){
    List<RestaurantDTO> mockRestaurants = Arrays.asList(
            new RestaurantDTO(1,"Restaurant 1", " Address 1", "city 1", "Description 1"),
            new RestaurantDTO(2,"Restaurant 2", " Address 2", "city 2", "Description 2")
    );
    when(restaurantService.fetchAllRestaurant()).thenReturn(mockRestaurants);

        ResponseEntity<List<RestaurantDTO>> response = restaurantController.fetchAllRestaurant();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRestaurants, response.getBody());

        verify(restaurantService, times(1)).fetchAllRestaurant();
    }

    @Test
    public void testSaveRestaurant(){
        RestaurantDTO mockRestaurant = new RestaurantDTO(1,"Restaurant 1", " Address 1", "city 1", "Description 1");

        when(restaurantService.addRestaurantInDB(mockRestaurant)).thenReturn(mockRestaurant);

        ResponseEntity<RestaurantDTO> response = restaurantController.saveRestaurant(mockRestaurant);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockRestaurant, response.getBody());

        verify(restaurantService,times(1)).addRestaurantInDB(mockRestaurant);
    }

    @Test
    public void fetchRestaurantById(){
        Integer mockRestaurantId =1;
        RestaurantDTO mockRestaurant = new RestaurantDTO(1,"Restaurant 1", " Address 1", "city 1", "Description 1");

        when(restaurantService.fetchRestaurantById(mockRestaurantId)).thenReturn(new ResponseEntity<>(mockRestaurant,HttpStatus.OK));

        ResponseEntity<RestaurantDTO> response = restaurantController.fetchById(mockRestaurantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRestaurant, response.getBody());

        verify(restaurantService,times(1)).fetchRestaurantById(mockRestaurantId);
    }
}
