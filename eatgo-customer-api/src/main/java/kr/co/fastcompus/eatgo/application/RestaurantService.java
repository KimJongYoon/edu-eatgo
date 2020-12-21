package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Getter
@Setter
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * 1건 조회
     * @param id
     * @return
     */
    public Restaurant getRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(()-> new RestaurantNotFoundException(id));

        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        List<Review> reviews = reviewRepository.findAllByRestaurantId(id);

        restaurant.setMenuItems(menuItems);
        restaurant.setReviews(reviews);
        return restaurant;
    }

    /**
     * 리스트 조회
     * @return
     * @param reion
     * @param categoryId
     */
    public List<Restaurant> getRestaurants(String reion, Long categoryId) {
        // TODO:: categoryId 작업 예정
        List<Restaurant> restaurants = null;


        if(StringUtils.isEmpty(reion)) restaurants = restaurantRepository.findAll();
        else restaurants = restaurantRepository.findAllByAddrContainingAndCategoryId(reion, categoryId);

        restaurants.forEach(e ->{
                e.setMenuItems(menuItemRepository.findAllByRestaurantId(e.getId()));
                e.setReviews(reviewRepository.findAllByRestaurantId(e.getId()));
        });
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
