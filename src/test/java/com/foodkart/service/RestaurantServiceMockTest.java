package com.foodkart.service;

import static org.junit.jupiter.api.Assertions.*;

import com.foodkart.entity.Restaurant;
import com.foodkart.repository.RestaurantRepository;
import com.foodkart.exception.RestaurantDoesNotExistException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RestaurantServiceMockTest {

    @Mock
    private RestaurantRepository restaurantRepoMock;
    @InjectMocks
    private RestaurantService restaurantServiceMock;

    private static Restaurant restaurant;

    @BeforeAll
    public static void setup(){
        restaurant = createTestRestaurant();
    }

    @Test
    public void testRegisterRestaurant(){
        when(restaurantServiceMock.registerRestaurant(restaurant)).thenReturn(restaurant);

        restaurantServiceMock.registerRestaurant(restaurant);

        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        verify(restaurantRepoMock).save(captor.capture());
        assertEquals(captor.getValue().getName(), restaurant.getName());
        assertEquals(captor.getValue().getRestaurantNumber(), restaurant.getRestaurantNumber());
        assertEquals(captor.getValue().getPincodes(), restaurant.getPincodes());
        assertEquals(captor.getValue().getFoodName(), restaurant.getFoodName());
        assertEquals(captor.getValue().getItemQuantity(), restaurant.getItemQuantity());
        assertEquals(captor.getValue().getFoodPrice(), restaurant.getFoodPrice());
    }

    @Test
    public void testUpdateQuantity() throws RestaurantDoesNotExistException {
        when(restaurantRepoMock.findByRestaurantNumber(1)).thenReturn(restaurant);

        restaurantServiceMock.updateQuantity(restaurant.getRestaurantNumber(), 20);

        verify(restaurantRepoMock).findByRestaurantNumber(1);
    }

    @Test
    public void testUpdateQuantityInvalid() {
        when(restaurantRepoMock.findByRestaurantNumber(20)).thenReturn(null);

        RestaurantDoesNotExistException thrown = assertThrows(
               RestaurantDoesNotExistException.class,
                () -> restaurantServiceMock.updateQuantity(20, 5),
                "Expected updateQuantity to throw, but it didn't");

        verify(restaurantRepoMock).findByRestaurantNumber(20);
        assertNotNull(thrown);
        assertTrue(thrown.getMessage().contains("does not exist."));
    }

    @Test
    public void testPlaceOrder() throws RestaurantDoesNotExistException {
        when(restaurantRepoMock.findByRestaurantNumber(1)).thenReturn(restaurant);

        restaurantServiceMock.placeOrder(restaurant.getRestaurantNumber(), 5);

        verify(restaurantRepoMock).findByRestaurantNumber(1);
    }

    @Test
    public void testPlaceOrderInvalid() {
        when(restaurantRepoMock.findByRestaurantNumber(20)).thenReturn(null);

        RestaurantDoesNotExistException thrown = assertThrows(
                RestaurantDoesNotExistException.class,
                () -> restaurantServiceMock.placeOrder(20, 5),
                "Expected updateQuantity to throw, but it didn't");

        verify(restaurantRepoMock).findByRestaurantNumber(20);
        assertNotNull(thrown);
        assertTrue(thrown.getMessage().contains("does not exist."));
    }

    @Test
    public void testPlaceOrderQuantityTooHigh() throws RestaurantDoesNotExistException {
        when(restaurantRepoMock.findByRestaurantNumber(1)).thenReturn(restaurant);

        // return false if quantity is too high
        assertFalse(restaurantServiceMock.placeOrder(restaurant.getRestaurantNumber(), 30));

        verify(restaurantRepoMock).findByRestaurantNumber(1);
    }

    @Test
    public void testFindByPrice() {
        when(restaurantRepoMock.findByFoodPrice(10)).thenReturn(List.of(restaurant));

        assertEquals(restaurantServiceMock.findByPrice(restaurant.getFoodPrice(), "HOB"), List.of("Test Restaurant"));

        verify(restaurantRepoMock).findByFoodPrice(10);
    }

    @Test
    public void testFindByPriceEmpty() {
        when(restaurantRepoMock.findByRestaurantNumber(20)).thenReturn(null);

        assertEquals(restaurantServiceMock.findByPrice(20, "HOB"), new LinkedList<String>());

        verify(restaurantRepoMock).findByFoodPrice(20);
    }

    private static Restaurant createTestRestaurant(){
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setRestaurantNumber(1);
        restaurant.setPincodes("HOB/NYC");
        restaurant.setFoodName("Burgers");
        restaurant.setItemQuantity(10);
        restaurant.setFoodPrice(10);

        return restaurant;
    }
}
