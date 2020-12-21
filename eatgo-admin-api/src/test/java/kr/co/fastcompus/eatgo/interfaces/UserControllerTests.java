package kr.co.fastcompus.eatgo.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fastcompus.eatgo.application.UserService;
import kr.co.fastcompus.eatgo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .email("tester@example.com")
                .name("테스터")
                .level(1L)
                .build());

        given(userService.getUsers()).willReturn(users);
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("테스터"));
        ;
    }

    /**
     * 추가
     * @throws Exception
     */
    @Test
    public void created() throws Exception {
        User mockUser = User.builder()
                .name("Administrator")
                .email("admin@example.com")
                .level(100L)
                .build();

        given(userService.addUser(mockUser.getEmail(), mockUser.getName())).will(invocation -> {
            String email = invocation.getArgument(0);
            String name = invocation.getArgument(1);
            User user = User.builder()
                    .id(1004L)
                    .email(email)
                    .name(name)
                    .level(1L)
                    .build();
            return user;
        });


        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"name\":\"%s\", \"email\":\"%s\", \"level\":\"%d\"}", mockUser.getName(), mockUser.getEmail(), mockUser.getLevel()))
        )
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(header().string("location", "/user/1004"))
        ;
        verify(userService).addUser(mockUser.getEmail(), mockUser.getName());
    }


    /**
     * 수정
     * @throws Exception
     */
    @Test
    public void update() throws Exception {
        User mockUser = User.builder()
                .name("Administrator")
                .email("admin@example.com")
                .level(10L)
                .build();


        mvc.perform(patch("/users/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"name\":\"%s\", \"email\":\"%s\", \"level\":\"%d\"}", mockUser.getName(), mockUser.getEmail(), mockUser.getLevel()))
        )
                .andExpect(status().isOk())
        ;

        verify(userService).updateUser(eq(1004L), eq(mockUser.getEmail()), eq(mockUser.getName()), eq(mockUser.getLevel()));
    }

    @Test
    public void deactivate() throws Exception {

        mvc.perform(delete("/users/1004"))
                .andExpect(status().isOk());

        verify(userService).deactivateUser(eq(1004L));
    }


}