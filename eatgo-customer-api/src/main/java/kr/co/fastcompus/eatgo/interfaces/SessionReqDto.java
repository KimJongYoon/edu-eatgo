package kr.co.fastcompus.eatgo.interfaces;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SessionReqDto {

    private String email;
    private String password;
}
