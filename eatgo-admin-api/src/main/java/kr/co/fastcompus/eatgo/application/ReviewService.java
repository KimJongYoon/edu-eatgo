package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.RestaurantRepository;
import kr.co.fastcompus.eatgo.domain.Review;
import kr.co.fastcompus.eatgo.domain.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;

    public Review addReviews(Long RestaurantId, Review review) {
        review.setRestaurantId(RestaurantId);
        return reviewRepository.save(review);
    }

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }
}
