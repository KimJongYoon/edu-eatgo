package kr.co.fastcompus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "region")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Region {

    @Id
    @GeneratedValue
    @Setter
    private Long id;

    private String name;
}
