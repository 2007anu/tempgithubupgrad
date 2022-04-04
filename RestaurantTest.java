import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    Restaurant restaurant;

    private Restaurant buildRestaurantData(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        return restaurant;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurant = buildRestaurantData();
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        Clock clock = Clock.fixed(Instant.parse("2022-04-02T11:15:30.00Z"), ZoneId.of("UTC"));
        LocalTime dateTime = LocalTime.now(clock);
        Mockito.when(restaurantSpy.getCurrentTime()).thenReturn(dateTime);
        assertEquals(true,  restaurantSpy.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurant = buildRestaurantData();
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        Clock clock = Clock.fixed(Instant.parse("2022-04-02T10:15:30.00Z"), ZoneId.of("UTC"));
        LocalTime dateTime = LocalTime.now(clock);
        Mockito.when(restaurantSpy.getCurrentTime()).thenReturn(dateTime);
        assertEquals(false,  restaurantSpy.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_equal_to_opening_time(){
        restaurant = buildRestaurantData();
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        Clock clock = Clock.fixed(Instant.parse("2022-04-02T10:30:00.00Z"), ZoneId.of("UTC"));
        LocalTime dateTime = LocalTime.now(clock);
        Mockito.when(restaurantSpy.getCurrentTime()).thenReturn(dateTime);
        assertEquals(true,  restaurantSpy.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_equal_to_closing_time(){
        restaurant = buildRestaurantData();
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        Clock clock = Clock.fixed(Instant.parse("2022-04-02T22:00:00.00Z"), ZoneId.of("UTC"));
        LocalTime dateTime = LocalTime.now(clock);
        Mockito.when(restaurantSpy.getCurrentTime()).thenReturn(dateTime);
        assertEquals(false,  restaurantSpy.isRestaurantOpen());

    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<<<Total Order Cost>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_total_order_cost_if_menu_contains_items_more_than_one(){
        //WRITE UNIT TEST CASE HERE
        restaurant = buildRestaurantData();
        int expectedTotalCost=388;
        assertEquals(expectedTotalCost,restaurant.getTotalOrderCost(restaurant.getMenu()));
    }

    @Test
    public void get_total_order_cost_if_menu_contains_zero_items() throws itemNotFoundException {
        //WRITE UNIT TEST CASE HERE
        restaurant = buildRestaurantData();
        restaurant.removeFromMenu("Sweet corn soup");
        restaurant.removeFromMenu("Vegetable lasagne");
        int expectedTotalCost=0;
        assertEquals(expectedTotalCost,restaurant.getTotalOrderCost(restaurant.getMenu()));
    }
        ///////////str///////////
    @Test
    public void get_total_order_cost_if_menu_contains_items_more_than_one_str(){
        //WRITE UNIT TEST CASE HERE
        restaurant = buildRestaurantData();
        List<String> itemList = new ArrayList<String>();
        itemList.add("Sweet corn soup");
        itemList.add("Vegetable lasagne");
        int expectedTotalCost=388;
        assertEquals(expectedTotalCost,restaurant.getTotalOrderCostString(itemList));
    }

    @Test
    public void get_total_order_cost_if_menu_contains_zero_items_str() throws itemNotFoundException {
        //WRITE UNIT TEST CASE HERE
        restaurant = buildRestaurantData();
        restaurant.removeFromMenu("Sweet corn soup");
        restaurant.removeFromMenu("Vegetable lasagne");
        List<String> itemList = new ArrayList<String>();
        int expectedTotalCost=0;
        assertEquals(expectedTotalCost,restaurant.getTotalOrderCostString(itemList));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<Total Order Cost>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant = buildRestaurantData();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant = buildRestaurantData();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant = buildRestaurantData();
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
