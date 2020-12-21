package kr.co.fastcompus.eatgo.interfaces;

import kr.co.fastcompus.eatgo.application.UserService;
import kr.co.fastcompus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    // 1. User List
    // 2. User Create -> 회원 가입
    // 3. User Updatr
    // 4. User Delete -> level => 아무 것도 못함
    // (1: Customer, 2: Restaurant Owner, 3. admin)

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> list() {
        List<User> users = userService.getUsers();
        return users;
    }

    @PostMapping("")
    public String create(@RequestBody User resource, HttpServletResponse response){

        User user = userService.addUser(resource.getEmail(), resource.getName());
        response.setStatus(HttpStatus.CREATED.value());
        response.setHeader("location", "/user/"+user.getId());
        return "{}";
    }

    @PatchMapping("/{id}")
    public String update(
            @PathVariable("id") Long id ,
            @RequestBody User resource){
        userService.updateUser(id, resource.getEmail(), resource.getName(), resource.getLevel());
        return "{}";
    }

    @DeleteMapping("/{id}")
    public String deactivate(
            @PathVariable("id") Long id
    ){
        userService.deactivateUser(id);
        return "{}";
    }
    
}
