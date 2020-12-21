package kr.co.fastcompus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Setter
    private Long id;

    @NotEmpty
    @Setter
    private String email;

    @NotEmpty
    @Setter
    private String name;

    @NotNull
    @Setter
    private Long level;

    private String password;

    public boolean isAdmin() {
        return level >= 100;
    }
    public boolean isActive() { return level > 0; }

    public void updateUser(String email, String name, Long level){
        this.email = email;
        this.name = name;
        this.level = level;
    }

    public void deactivate() {
        this.level = 0L;
    }

}
