package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Getter
@Setter
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * 1건 조회
     * @param id
     * @return
     */
    public Restaurant getRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(()-> new RestaurantNotFoundException(id));

        return restaurant;
    }

    /**
     * 리스트 조회
     * @return
     */
    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants;
    }

    /**
     * 추가
     * @param restaurant
     * @return
     */
    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    /**
     * 수정
     * save()를 사용하지 않고 @Transactional 어노테이션으로 메서드 범위를 묶어서 updateInfomation() 메서드로 처리한다.
     * @param id
     * @param name
     * @param addr
     * @return
     */
    @Transactional
    public Restaurant updateRestaurants(Long id, String name, String addr) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        restaurant.updateInfomation(name, addr);
        return restaurant;
    }
}
