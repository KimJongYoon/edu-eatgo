package kr.co.fastcompus.eatgo.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.fastcompus.eatgo.application.EmailNotExitedException;
import kr.co.fastcompus.eatgo.application.PasswordWrongException;
import kr.co.fastcompus.eatgo.application.UserService;
import kr.co.fastcompus.eatgo.domain.User;
import kr.co.fastcompus.eatgo.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;


    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 데이터 추가 테스트
     * (유효성 검사가 통과되는 경우)
     * @throws Exception
     */
    @Test
    public void createWithValidAttributes() throws Exception {

        String email = "tester@example.com";
        String password = "test";

        long id = 1234L;
        String name = "John";
        User mockUser = User.builder()
                .id(id)
                .name(name)
                .password("ACCESSTOKEN")
                .build();

        given(userService.authenticate(email, password)).willReturn(mockUser);
        given(jwtUtil.createToken(id, name)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"")))
        ;


        verify(userService).authenticate(eq(email), eq(password));
    }

    /**
     * 암호가 틀릴 경우
     * @throws Exception
     */
    @Test
    public void createWithWrongPassword() throws Exception {

        given(userService.authenticate("tester@example.com", "x")).willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest())
        ;

        verify(userService).authenticate(eq("tester@example.com"), eq("x"));
    }

    /**
     * 이메일 없을 경우
     * @throws Exception
     */
    @Test
    public void createWithNotExistedEmail() throws Exception {

        given(userService.authenticate("x@example.com", "test")).willThrow(EmailNotExitedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@example.com\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest())
        ;

        verify(userService).authenticate(eq("x@example.com"), eq("test"));
    }


}