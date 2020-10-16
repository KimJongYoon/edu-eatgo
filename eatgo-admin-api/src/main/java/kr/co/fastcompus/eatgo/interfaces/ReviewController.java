package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.ReviewService;
import kr.co.fastcompus.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public List<Review> list() {
        return  reviewService.getReviews();
    }


}
