package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.RestaurantService;
import kr.co.fastcompus.eatgo.domain.MenuItem;
import kr.co.fastcompus.eatgo.domain.Restaurant;
import kr.co.fastcompus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcompus.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private RestaurantService restaurantService;

    /**
     * 목록 테스트
     *
     * @throws Exception
     */
    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .build());
        given(restaurantService.getRestaurants("Seoul", 1L)).willReturn(restaurants);

        mvc.perform(
                get("/restaurants?region=Seoul&category=1")
        )
                .andDo(print()) // 테스트 결과를 console에 표시
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
        ;
    }


    /**
     * 상세 화면 테스트(데이터가 존재하는 경우)
     *
     * @throws Exception
     */
    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant = Restaurant.builder() // 가짜객체
                .id(1004L)
                .name("Bob zip")
                .build();
        MenuItem menuItem = MenuItem.builder()
                .menu("Kimchi")
                .build();
        restaurant.setMenuItems(Arrays.asList(menuItem));


        Review review = Review.builder()
                .name("JOKER")
                .score(5)
                .description("Great!")
                .build();
        restaurant.setReviews(Arrays.asList(review));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);
        mvc.perform(
                get("/restaurants/1004")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
                .andExpect(jsonPath("$.name").value("Bob zip")) // jsonPath를 통해 content를 가독성 좋게 체크할 수 있음
                .andExpect(jsonPath("$.reviews[0].score").value(5))
        ;

    }

    /**
     * 상세 화면 테스트(데이터가 존재하지 않는 경우)
     *
     * @throws Exception
     */
    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurant(404L))
                .willThrow(new RestaurantNotFoundException(404L));
        mvc.perform(get("/restaurants/404"))
                    .andExpect(status().isNotFound()) // 404 예외가 발생되면 통과
                    .andExpect(content().string("{}"))
        ;
    }


}