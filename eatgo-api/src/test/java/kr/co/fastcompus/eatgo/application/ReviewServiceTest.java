package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.Review;
import kr.co.fastcompus.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addReview() {
        given(reviewRepository.save(any())).willReturn(
                Review.builder()
                        .id(1L)
                        .build()
        );

        Review review = Review.builder()
                .name("KJY")
                .description("맛있다")
                .build();

        Review created =  reviewService.addReview(1004L, review);

        verify(reviewRepository).save(any());
        assertThat(created.getId(), is(1L));

    }
}