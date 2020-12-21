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
        given(reviewRepository.save(any())).will(invocation -> {
            Review review = invocation.getArgument(0);
            review.setId(1L);
            return review;
        });

        Review created =  reviewService.addReview(1004L, "KJY", 3, "맛있다");
        System.out.println(created);

        verify(reviewRepository).save(any());
        assertThat(created.getId(), is(1L));
        assertThat(created.getRestaurantId(), is(1004L));

    }
}