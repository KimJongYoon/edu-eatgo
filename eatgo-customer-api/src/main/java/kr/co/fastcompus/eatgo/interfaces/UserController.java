package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.UserService;
import kr.co.fastcompus.eatgo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public String create(@RequestBody User resource, HttpServletResponse response){

        User user = userService.registerUser(resource.getEmail(), resource.getName(), resource.getPassword());


        response.setStatus(HttpStatus.CREATED.value());
        response.setHeader("location", "/users/" + user.getId());
        return "{}";
    }

}
