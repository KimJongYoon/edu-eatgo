package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.EmailExistedException;
import kr.co.fastcompus.eatgo.domain.User;
import kr.co.fastcompus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerUser(){
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        User user = userService.registerUser(email, name, password);

        verify(userRepository).save(any());


    }

    /**
     * 이메일이 중복일 경우
     */
    //    @Test(expected = RestaurantNotFoundException.class) // jUnit 5에서 사용불가능
    @Test
    public void registerUserWithExistedEmail(){
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";

        User mockUser = User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        given(userRepository.findAllByEmail(mockUser.getEmail())).willReturn(Optional.of(mockUser) );

        Exception e = assertThrows(EmailExistedException.class,()->{
            userService.registerUser(email, name, password);
        });

        verify(userRepository, never()).save(any()); // 저장을 안해야된다.

    }

    /**
     * 인증(올바른 속성일 경우)
     */
    @Test
    public void authenticateWithValidAttributes(){
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email)
                .password(password)
                .build();

        given(userRepository.findAllByEmail(email)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(),any())).willReturn(true); // 비밀번호 맞게게

       User user = userService.authenticate(email, password);

        assertThat(user.getEmail(), is(email));


    }
    /**
     * 인증(이메일이 없을 경우)
     */
    @Test
    public void authenticateWithNotExistedEmail(){
        String email = "x@example.com";
        String password = "test";

        given(userRepository.findAllByEmail(email)).willReturn(Optional.empty());

        assertThrows(EmailNotExitedException.class, () -> userService.authenticate(email, password));

    }
    /**
     * 인증(비밀번호 틀릴 경우)
     */
    @Test
    public void authenticateWithWrongPassword(){
        String email = "x@example.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email)
                .password("x")
                .build();

        given(userRepository.findAllByEmail(email)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(),any())).willReturn(false); // 비밀번호 틀리게

        assertThrows(PasswordWrongException.class, () -> userService.authenticate(email, password));

    }

}