package kr.co.fastcompus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CategoryTests {

    @Test
    public void creation(){
        Category category = Category.builder().name("Korean Food").build();
        assertThat(category.getName(), is("Korean Food"));
    }

}