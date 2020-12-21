package kr.co.fastcompus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    // 리스트 조회
    List<Restaurant> findAll();

    // 리전, 카테고리 필터링
    List<Restaurant> findAllByAddrContainingAndCategoryId(
            String region, Long categoryId);

    // id 검색
    Optional<Restaurant> findById(Long id);

    Restaurant save(Restaurant restaurant);


}
