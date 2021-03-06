package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

//@SpringBootTest
//@AutoConfigureWebMvc
//@RunWith(SpringRunner.class)
class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .addr("Seoul")
                .name("Bob zip")
                .menuItems(new ArrayList<>())
                .build();
        restaurants.add(restaurant);
        given(restaurantRepository.findAllByAddrContainingAndCategoryId("Seoul", 1L)).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

    }

    private void mockMenuItemRepository() {
        MenuItem menuItem = MenuItem.builder()
                .id(2004L)
                .restaurantId(1004L)
                .menu("Kim chi")
                .build();
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(Arrays.asList(menuItem));
    }

    private void mockReviewRepository() {
        Review review = Review.builder()
                .id(3004L)
                .restaurantId(1004L)
                .name("JOKER")
                .score(5)
                .description("맛있어요")
                .build();
        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(Arrays.asList(review));
    }

    /**
     * 1개 조회(데이터가 있는 경우)
     */
    @Test
    public void getRestaurantWithExisted() {
        Restaurant restaurant = restaurantService.getRestaurant(1004L);
        MenuItem menuItem = restaurant.getMenuItems().get(0);
        Review review = restaurant.getReviews().get(0);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));
        verify(reviewRepository).findAllByRestaurantId(eq(1004L));

        assertThat(restaurant.getId(), is(1004L));
        assertThat(menuItem.getId(), is(2004L));
        assertThat(review.getId(), is(3004L));
    }
    /**
     * 1개 조회(데이터가 없는 경우)
     */
//    @Test(expected = RestaurantNotFoundException.class) // jUnit 5에서 사용불가능
    @Test
    public void getRestaurantWithNotExisted() {
        //jUnit 5 형식으로 예외 확인
        Exception e = assertThrows(RestaurantNotFoundException.class,()->{
            restaurantService.getRestaurant(404L);
        });
    }

    /**
     * 전체 조회
     */
    @Test
    public void getRestaurants() {
        String region = "Seoul";
        long categoryId = 1L;

        List<Restaurant> restaurants = restaurantService.getRestaurants(region, categoryId);

        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));
        verify(reviewRepository).findAllByRestaurantId(eq(1004L));
        verify(restaurantRepository).findAllByAddrContainingAndCategoryId(eq("Seoul"), eq(1L));

        assertThat(restaurants.get(0).getId(), is(1004L));
        assertThat(restaurants.get(0).getCategoryId(), is(1L));

    }

    /**
     * 추가
     */
    @Test
    public void addRestaurants() {

        given(restaurantRepository.save(any())).will(invocation -> { 
           Restaurant restaurant = invocation.getArgument(0);
           restaurant.setId(1234L);
           return restaurant;
        });

        Restaurant restaurant = new Restaurant("BeRyong", "Busan");

        Restaurant created = restaurantService.addRestaurant(restaurant);

        assertThat(created.getId(), is(1234L));
    }

    /**
     * 수정
     */

    @Test
    public void updateRestaurant() {
        Restaurant restaurant = Restaurant.builder().id(1004L).name("Bob Zip").addr("Seoul").build();

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant)); // Service 내에서 실행되어지는 리포지토리 메서드. findById(1004L)을 실행하면 restaurant 값을 돌려준다.

        restaurantService.updateRestaurants(1004L, "Sool Zip", "Busan"); // 서울에서 부산으로 이사가고 술집으로 이름을 바꿨을 때

        assertThat(restaurant.getName(), is("Sool Zip")); // 가게의 이름은 술집이다.
        assertThat(restaurant.getAddr(), is("Busan")); // 가게의 이름은 술집이다.
    }
}