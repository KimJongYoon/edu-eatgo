package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.Category;
import kr.co.fastcompus.eatgo.domain.CategoryRepository;
import kr.co.fastcompus.eatgo.domain.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CategoryServiceTests {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 카테고리 목록
     */
    @Test
    public void list(){
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(Category.builder().name("Seoul").build());

        given(categoryRepository.findAll()).willReturn(mockCategories);

        List<Category> getCategory = categoryService.getCategories();
        assertThat(getCategory.get(0).getName(), is("Seoul"));

    }

    /**
     * 카테고리 등록
     */
    @Test
    public void create(){
        Category mockRegion = Category.builder().name("Seoul").build();

        given(categoryRepository.save(any())).will(invocation -> {
            Category savedCategory = invocation.getArgument(0);
            savedCategory.setId(1234L);
            return savedCategory;
        });

        Category category = categoryService.addCategory(mockRegion);
        assertThat(category.getId(), is(1234L));

        verify(categoryRepository).save(any());
    }

}