package kr.co.fastcompus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

@Entity(name = "menuItem")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MenuItem {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private Long restaurantId;

    private String menu;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT) // false가 defualt 값이기 때문에 false일 경우 조회할 때 보이지 않는다.
    @Transient // DB에 넣지 않는다.
    private boolean destroy;
}
