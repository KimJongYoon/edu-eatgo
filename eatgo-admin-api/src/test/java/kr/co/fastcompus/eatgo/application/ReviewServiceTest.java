package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.Review;
import kr.co.fastcompus.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void addReviews() {
        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(Review.builder().description("Cool!").build());

        given(reviewRepository.findAll()).willReturn(mockReviews);

        List<Review> reviews = reviewService.getReviews();
        Review review = reviews.get(0);

        assertThat(review.getDescription(), is("Cool!"));

    }

    @Test
    void addReview() {
        given(reviewRepository.save(any())).will(invocation -> {
            Review review = invocation.getArgument(0);
            review.setId(1L);
            return review;
        });

        Review review = Review.builder()
                .name("KJY")
                .description("맛있다")
                .build();

        Review created =  reviewService.addReviews(1004L, review);
        System.out.println(created);

        verify(reviewRepository).save(any());
        assertThat(created.getId(), is(1L));
        assertThat(created.getRestaurantId(), is(1004L));

    }
}