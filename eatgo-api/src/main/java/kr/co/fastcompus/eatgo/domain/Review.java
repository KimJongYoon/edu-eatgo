package kr.co.fastcompus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Review {

    @Id
    @GeneratedValue
    @Setter
    Long id;

    @Setter
    Long restaurantId;

    @NotEmpty
    String name;

    @Min(0)
    @Max(5)
    Integer score;

    String description;
}
