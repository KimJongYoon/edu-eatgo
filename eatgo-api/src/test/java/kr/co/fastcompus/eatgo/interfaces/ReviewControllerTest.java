package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.ReviewService;
import kr.co.fastcompus.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;


    /**
     * 데이터 추가 테스트
     * (유효성 검사가 통과되는 경우)
     * @throws Exception
     */
    @Test
    public void createWithValidAttritues() throws Exception {
        given(reviewService.addReview(eq(1L), any())).will(invocation -> {
           Review review = invocation.getArgument(0);
            review.setId(1234L);

           return review;
        });

        mvc.perform(post("/restaurants/1/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\" : \"JOKER\", \"score\" : 3, \"description\" : \"맛있다\"}")
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1/reviews/1234"))
                .andExpect(content().json("{}"))
        ;
        verify(reviewService).addReview(eq(1L), any());
    }

    /**
     * 데이터 추가 테스트
     * (유효성 검사가 통과 되지 않는 경우)
     * @throws Exception
     */
    @Test
    public void createWithInvalidAttritues() throws Exception {

        mvc.perform(post("/restaurants/1/reviews")
            .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;

        verify(reviewService, never()).addReview(eq(1L), any()); // BadRequest일 경우 한 번도 호출이 안되는걸 검증
    }
}