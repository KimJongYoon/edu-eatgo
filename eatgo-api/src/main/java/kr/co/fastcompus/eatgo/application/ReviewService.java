package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.RestaurantRepository;
import kr.co.fastcompus.eatgo.domain.Review;
import kr.co.fastcompus.eatgo.domain.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;

    public Review addReview(Long RestaurantId,Review review) {
       return reviewRepository.save(review);
    }
}
