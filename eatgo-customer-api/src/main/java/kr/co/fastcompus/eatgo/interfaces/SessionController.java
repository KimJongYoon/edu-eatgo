package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.UserService;
import kr.co.fastcompus.eatgo.domain.User;
import kr.co.fastcompus.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/session")
public class SessionController {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("")
    public SessionResDto create(
            @RequestBody SessionReqDto resource
            , HttpServletResponse response){

        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email, password);
        String accessToken = jwtUtil.createToken(user.getId(), user.getName());

        response.setStatus(HttpStatus.CREATED.value());
        response.setHeader("location", "/session");
        return SessionResDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
