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
class EatgoCustomerApiApplicationTests {

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
	}

	/**
	 * 초기 데이터 확인
	 */
	public void getEmptyRestaurants() throws Exception {
		System.out.println("TEST1");
		mvc.perform(
				get("/restaurants")
						.contentType(MediaType.APPLICATION_JSON)
		)
//				.andDo(print())
				.andExpect(status().isOk())
		;
	}


}
