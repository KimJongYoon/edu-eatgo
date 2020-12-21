package kr.co.fastcompus.eatgo.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fastcompus.eatgo.application.CategoryService;
import kr.co.fastcompus.eatgo.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    /**
     * 카테고리 목록 테스트
     * */
    @Test
    public void list() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().name("Seoul").build());

        given(categoryService.getCategories()).willReturn(categories);

        mvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Seoul"))
        ;
    }

    /**
     * 카테고리 추가
     */

    @Test
    public void create() throws Exception {
        Category category = Category.builder().name("Seoul").build();
        ObjectMapper op = new ObjectMapper();

        // regionService.addRegion() 실행 시 반환할 가짜 객체
        given(categoryService.addCategory(any())).will(invocation -> {
            Category category1 = invocation.getArgument(0);
            category1.setId(1234L);
            return category1;
        });

        mvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(op.writeValueAsString(category))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/categories/1234"))
        ;

        verify(categoryService).addCategory(any()); // regionService.addRegion가 실행되었는지 확인
    }
}