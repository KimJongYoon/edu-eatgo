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

//@SpringBootTest
//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)

//아래 WebMvcTest 대신에 @SpringBootTest, @AutoConfigureMockMvc 2개 어노테이션 사용
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
                .name("Bob zip")
                .build());
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(
                get("/restaurants")
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

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant);
        mvc.perform(
                get("/restaurants/1004")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
                .andExpect(jsonPath("$.name").value("Bob zip")) // jsonPath를 통해 content를 가독성 좋게 체크할 수 있음
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

    /**
     * 데이터 추가 테스트
     * (유효성 검사가 통과 되는 경우)
     * @throws Exception
     */
    @Test
    public void createWithValidData() throws Exception {

        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);

            return restaurant;
        });

        mvc.perform(
                post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON) // 미디어 타입 설정
                        .content("{\"name\" : \"BeRyong\", \"addr\" : \"Busan\"}")
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1234"))
                .andExpect(content().string("{}"))
        ;

        verify(restaurantService).addRestaurant(any()); // 실제로 이 메서드가    실행되었는지 확인
    }

    /**
     * 데이터 추가 테스트
     * (유효성 검사가 통과 되지 않는 경우)
     * @throws Exception
     */
    @Test
    public void createWithInvalidData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);

            return restaurant;
        });

        mvc.perform(
                post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON) // 미디어 타입 설정
                        .content("{\"name\" : \"\", \"addr\" : \"\"}")
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;

    }

    /**
     * 데이터 수정 테스트
     * (유효성 검사 통과)
     */

    @Test
    public void updateWithValidData() throws Exception {
        // 1004L, "JOKER House", "Seoul"

        mvc.perform(
                patch("/restaurants/1004")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"JOKER Bar\", \"addr\" : \"Busan\"}")
        ).andExpect(status().isOk());
        verify(restaurantService).updateRestaurants(1004L, "JOKER Bar", "Busan");

    }

    /**
     * 데이터 수정 테스트
     * (유효성 검사 미 통과)
     * @throws Exception
     */
    @Test
    public void updateWithInvalidData() throws Exception {
        // 1004L, "JOKER House", "Seoul"

        mvc.perform(
                patch("/restaurants/1004")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"\", \"addr\" : \"\"}")
        ).andExpect(status().isBadRequest());

    }
}