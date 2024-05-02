package com.aartiworkspace.restaurantlisting.services;

import com.aartiworkspace.restaurantlisting.dto.RestaurantDTO;
import com.aartiworkspace.restaurantlisting.entity.Restaurant;
import com.aartiworkspace.restaurantlisting.mapper.RestaurantMapper;
import com.aartiworkspace.restaurantlisting.repositories.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestaurantServiceTest {

    @Mock
    RestaurantRepository repo;

    @InjectMocks
    RestaurantService restaurantService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAllRestaurant(){

        List<Restaurant> mockRestaurants = Arrays.asList(
                new Restaurant(1,"Restaurant 1", " Address 1", "city 1", "Description 1"),
                new Restaurant(2,"Restaurant 2", " Address 2", "city 2", "Description 2")
        );

        when(repo.findAll()).thenReturn(mockRestaurants);

        List<RestaurantDTO> restaurantDTOList = restaurantService.fetchAllRestaurant();

        assertEquals(mockRestaurants.size(), restaurantDTOList.size());
        for(int i=0; i<mockRestaurants.size(); i++){
            RestaurantDTO expectedDTO = RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(mockRestaurants.get(i));
            assertEquals(expectedDTO, restaurantDTOList.get(i));
        }
        verify(repo, times(1)).findAll();

    }

    @Test
    public void testAddRestaurantInDB(){

       RestaurantDTO mockRestaurantDTO = new RestaurantDTO(1,"Restaurant 1", " Address 1", "city 1", "Description 1");
       Restaurant mockRestaurant = RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(mockRestaurantDTO);

       when(repo.save(mockRestaurant)).thenReturn(mockRestaurant);

       RestaurantDTO savedRestaurantDTO = restaurantService.addRestaurantInDB(mockRestaurantDTO);

       assertEquals(mockRestaurantDTO, savedRestaurantDTO);
       verify(repo, times(1)).save(mockRestaurant);

    }

    @Test
    public void testFetchRestaurantById_ExistingId(){
        Integer mockRestaurantId = 1;

        Restaurant mockRestaurant = new Restaurant(1,"Restaurant 1", " Address 1", "city 1", "Description 1");

        when(repo.findById(mockRestaurantId)).thenReturn(Optional.of(mockRestaurant));

        ResponseEntity<RestaurantDTO> response = restaurantService.fetchRestaurantById(mockRestaurantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRestaurantId, response.getBody().getId());

        verify(repo,times(1)).findById(mockRestaurantId);
    }

    @Test
    public void testFetchRestaurantById_NonExistingId(){
        Integer mockRestaurantId = 1;

        when(repo.findById(mockRestaurantId)).thenReturn(Optional.empty());

        ResponseEntity<RestaurantDTO> response = restaurantService.fetchRestaurantById(mockRestaurantId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());

        verify(repo,times(1)).findById(mockRestaurantId);
    }
}
