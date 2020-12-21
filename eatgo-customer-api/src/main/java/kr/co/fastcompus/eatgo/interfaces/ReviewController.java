package kr.co.fastcompus.eatgo.interfaces;

import io.jsonwebtoken.Claims;
import kr.co.fastcompus.eatgo.application.ReviewService;
import kr.co.fastcompus.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(
            Authentication authentication // 스프링의 시큐리티의 인증 인터페이스를 바로 사용하여 토큰 정보를 가져올 수 있다
            , @PathVariable("restaurantId") Long restaurantId
            , @RequestBody @Valid Review resource
            ) throws URISyntaxException {

        Claims claims = (Claims) authentication.getPrincipal();
        String name = claims.get("name", String.class);
        Integer score = resource.getScore();
        String description = resource.getDescription();

        Review review = reviewService.addReview(restaurantId, name, score, description);
        URI location = new URI("/restaurants/"+ restaurantId +"/reviews/"+review.getId());
        return ResponseEntity.created(location).body("{}");
    }

}
