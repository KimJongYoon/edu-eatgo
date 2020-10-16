package kr.co.fastcompus.eatgo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import kr.co.fastcompus.eatgo.application.RestaurantService;
import kr.co.fastcompus.eatgo.domain.MenuItem;
import kr.co.fastcompus.eatgo.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.Context;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc // 통합 테스트 시 사용
class EatgoAdminApiApplicationTests {

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext ctx;

	@BeforeEach
	public void setup() throws Exception {
		// mvc 한글깨짐 처리
		mvc = MockMvcBuilders.webAppContextSetup(ctx)
				.addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
				.alwaysDo(print())
				.build();

	}

	@Test
	public void totalTest() throws Exception {
		getEmptyRestaurants();
		String restaurantId = addRestaurants();
		addMenu(restaurantId);
	}

	/**
	 * 초기 데이터 비어있는지 확인
	 */
	public void getEmptyRestaurants() throws Exception {
		System.out.println("TEST1");
		mvc.perform(
				get("http://localhost:8080/restaurants")
				.contentType(MediaType.APPLICATION_JSON)
		)
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("[]"));
		;
	}

	/**
	 * 1)레스토랑 데이터 입력
	 * 2)메뉴,bulk 수정, 삭제
	 *
	 */
	public String addRestaurants() throws Exception {
		Restaurant restaurant = Restaurant.builder()
				.addr("Seoul")
				.name("화룡정점")
				.build();

		ObjectMapper op = new ObjectMapper();

		// 데이터 등록
		MvcResult mvcResult = mvc.perform(post("http://localhost:8080/restaurants")
				.contentType(MediaType.APPLICATION_JSON)
				.content(op.writeValueAsString(restaurant))
		)
//		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(header().exists("location"))
		.andReturn()
		;

		String retaurantId = mvcResult.getResponse().getHeader("Location");

		// 데이터 조회
		mvc.perform(
				get("http://localhost:8080/" + retaurantId)
						.contentType(MediaType.APPLICATION_JSON)
		)
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(restaurant.getName()))
				.andExpect(jsonPath("$.addr").value(restaurant.getAddr()))
		;
		return retaurantId;
	}
	/**
	 * 1)레스토랑 데이터 입력
	 * 2)메뉴,bulk 수정, 삭제
	 *
	 */
	public void addMenu(String retaurantId) throws Exception {
		List<MenuItem> menuItemList = Arrays.asList(
				MenuItem.builder()
						.menu("자장면")
						.build()
				,
				MenuItem.builder()
						.menu("짬뽕")
						.build()
		);

		ObjectMapper op = new ObjectMapper();


		// 메뉴 등록
		mvc.perform(
				patch("http://localhost:8080/" + retaurantId + "/menuitems" )
				.contentType(MediaType.APPLICATION_JSON)
				.content(op.writeValueAsString(menuItemList))
		)
//		.andDo(print())
		.andExpect(status().isOk())
		;


		// 데이터 조회
		mvc.perform(
				get("http://localhost:8080/" + retaurantId)
						.contentType(MediaType.APPLICATION_JSON)
		)
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.menuItems[0].menu").value(menuItemList.get(0).getMenu()))
				.andExpect(jsonPath("$.menuItems[1].menu").value(menuItemList.get(1).getMenu()))
		;

		//메뉴 수정 및 삭제 한번에 하기
		menuItemList = Arrays.asList(
				MenuItem.builder()
						.id(2L)
						.menu("탕수육")
						.build()
				,
				MenuItem.builder()
						.id(3L)
						.destroy(true)
						.build()
		);
		mvc.perform(
				patch("http://localhost:8080/" + retaurantId + "/menuitems" )
						.contentType(MediaType.APPLICATION_JSON)
						.content(op.writeValueAsString(menuItemList))
		)
//				.andDo(print())
				.andExpect(status().isOk())
		;

		// 데이터 조회
		mvc.perform(
				get("http://localhost:8080/" + retaurantId)
						.contentType(MediaType.APPLICATION_JSON)
		)
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.menuItems[0].menu").value(menuItemList.get(0).getMenu()))
				.andExpect(jsonPath("$.menuItems[1].menu").doesNotExist())
		;
	}

}
