package kr.co.fastcompus.eatgo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import kr.co.fastcompus.eatgo.application.RestaurantService;
import kr.co.fastcompus.eatgo.domain.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.Context;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc // 통합 테스트 시 사용
class EatgoApplicationTests {

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private MockMvc mvc;

	/**
	 * 초기 데이터 비어있는지 확인
	 */
	@Test
	public void test1() throws Exception {
		mvc.perform(
				get("http://localhost:8080/restaurants")
				.contentType(MediaType.APPLICATION_JSON)
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("[]"));
		;
	}

	/**
	 * 레스토랑 데이터 입력 및 데이터 확인
	 */
	@Test
	public void test2() throws Exception {
		Restaurant restaurant = Restaurant.builder()
				.addr("Seoul")
				.name("화룡정점")
				.build();
		ObjectMapper op = new ObjectMapper();
		mvc.perform(post("http://localhost:8080/restaurants")
				.contentType(MediaType.APPLICATION_JSON)
				.content(op.writeValueAsString(restaurant))
		).andDo(print())
		.andExpect(status().isCreated())
		.andExpect(header().exists("location"))
				;
	}

}
