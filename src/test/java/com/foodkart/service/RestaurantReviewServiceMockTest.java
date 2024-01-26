package com.foodkart.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.foodkart.entity.Restaurant;
import com.foodkart.entity.RestaurantReview;
import com.foodkart.repository.RestaurantReviewRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RestaurantReviewServiceMockTest {

    @Mock
    private RestaurantReviewRepository restaurantReviewRepoMock;
    @InjectMocks
    private RestaurantReviewService restaurantReviewServiceMock;

    private static RestaurantReview restaurantReview;

    @BeforeAll
    public static void setup(){
        restaurantReview = createTestRestaurantReview();
    }

    @Test
    public void testRegisterRestaurantReview(){
        when(restaurantReviewServiceMock.registerRestaurantReview(restaurantReview)).thenReturn(restaurantReview);

        restaurantReviewServiceMock.registerRestaurantReview(restaurantReview);

        ArgumentCaptor<RestaurantReview> captor = ArgumentCaptor.forClass(RestaurantReview.class);
        verify(restaurantReviewRepoMock).save(captor.capture());
        assertEquals(captor.getValue().getComment(), restaurantReview.getComment());
        assertEquals(captor.getValue().getRating(), restaurantReview.getRating());
        assertEquals(captor.getValue().getReviewNumber(), restaurantReview.getReviewNumber());
    }

    @Test
    public void testFindByRating(){
        when(restaurantReviewServiceMock.registerRestaurantReview(restaurantReview)).thenReturn(restaurantReview);
        when(restaurantReviewServiceMock.findByRating(5)).thenReturn(List.of(restaurantReview.getName()));

        restaurantReviewServiceMock.registerRestaurantReview(restaurantReview);
        restaurantReviewServiceMock.findByRating(5);

        verify(restaurantReviewRepoMock, times(1)).save(restaurantReview);
        verify(restaurantReviewRepoMock).findByRating(5);

    }

    private static RestaurantReview createTestRestaurantReview(){
        RestaurantReview restaurantReview = new RestaurantReview();
        restaurantReview.setName("Test Restaurant");
        restaurantReview.setComment("Best in Town!");
        restaurantReview.setRating(5);
        return restaurantReview;
    }

}
