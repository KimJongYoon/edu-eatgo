package kr.co.fastcompus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "restaurant")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Restaurant {

    @Id
    @GeneratedValue
    @Setter
    private Long id;

    @NotNull
    @Setter
    private Long categoryId;

    @NotEmpty
    private String name;
    @NotEmpty
    private String addr;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient // 이 어노테이션을 없애면 에러 발생. DB에 넣지 않는다라는 뜻
    private List<MenuItem> menuItems;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private List<Review> reviews;

    public Restaurant(String name, String addr) {
        this.name = name;
        this.addr = addr;
    }



    public String getInformation() {
        return name + " in " + this.addr;
    }

    public void setMenuItems(List<MenuItem> menuItems) {

        this.menuItems = new ArrayList<>(menuItems);
    }

    public void updateInfomation(String name, String addr) {
        this.name = name;
        this.addr = addr;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }
}
