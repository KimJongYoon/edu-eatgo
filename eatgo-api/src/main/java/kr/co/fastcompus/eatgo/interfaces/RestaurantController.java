package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.RestaurantService;
import kr.co.fastcompus.eatgo.domain.Restaurant;
import kr.co.fastcompus.eatgo.domain.RestaurantNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable(value = "id") long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        return restaurant;
    }

    @PostMapping("/restaurants")
    public ResponseEntity<?>  create(@Valid @RequestBody Restaurant restaurant) throws URISyntaxException {
        restaurantService.addRestaurant(restaurant);
        URI location = new URI("/restaurants/" + restaurant.getId());
        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restaurants/{id}")
    public String update(@PathVariable("id") Long id
                        ,@Valid @RequestBody Restaurant resource){
        String name = resource.getName();
        String addr = resource.getAddr();
        restaurantService.updateRestaurants(id, name, addr);
        return "{}";
    }
}
