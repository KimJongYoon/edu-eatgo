package kr.co.fastcompus.eatgo.interfaces;

import lombok.Builder;
import lombok.Data;

/**
 * 순수하게 데이터로만 쓰이는 Dto에 @Data 에노테이션 사용
 * =전달
 */
@Builder
@Data
public class SessionResDto {

    private String accessToken;
}
