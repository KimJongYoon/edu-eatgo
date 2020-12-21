package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.User;
import kr.co.fastcompus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTests {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUser(){
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .email("tester@example.com")
                .name("테스터")
                .level(1L)
                .build());

        given(userRepository.findAll()).willReturn(mockUsers);

         List<User> users = userService.getUsers();

         assertThat(users.get(0).getName(), is("테스터"));
    }

    /**
     * 추가
     */
    @Test
    public void addUser() {
        User mockUser = User.builder()
                .name("Administrator")
                .email("admin@example.com")
                .level(100L)
                .build();

        given(userRepository.save(any())).will(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        User createdUser = userService.addUser(mockUser.getEmail(), mockUser.getName());

        verify(userRepository).save(any());
        assertThat(createdUser.getId(), is(1L));
    }

    /**
     * 수정
     */
    @Test
    public void updateUser(){
        User mockUser = User.builder()
                .id(1004L)
                .name("Administrator") // 이름 변경
                .email("admin@example.com")
                .level(1L)
                .build();

        given(userRepository.findAllById(mockUser.getId())).willReturn(Optional.of(mockUser));

        User user =  userService.updateUser(mockUser.getId(), mockUser.getEmail(), "Superman", 100L);

        verify(userRepository).findAllById(eq(mockUser.getId()));

        assertThat(user.getName(), is("Superman"));
        assertThat(user.isAdmin(), is(true));
    }


    @Test
    public void deactivateUser(){
        User mockUser = User.builder()
                .id(1004L)
                .name("Administrator") // 이름 변경
                .email("admin@example.com")
                .level(1L)
                .build();

        given(userRepository.findAllById(mockUser.getId())).willReturn(Optional.of(mockUser));

        User user =  userService.deactivateUser(mockUser.getId());

        verify(userRepository).findAllById(eq(mockUser.getId()));
        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(false));
    }
}