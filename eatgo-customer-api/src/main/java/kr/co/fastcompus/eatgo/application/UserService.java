package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.EmailExistedException;
import kr.co.fastcompus.eatgo.domain.User;
import kr.co.fastcompus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String email, String name, String password) {

        Optional<User> existed = userRepository.findAllByEmail(email);

        // 이미 등록 된 이메일이 있으면 예외외
       if(existed.isPresent()){ // not null
            throw new EmailExistedException(email);
        }

        User user = User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .level(1L)
                .build();

        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        // 이메일이 없을 때 예외
        User user = userRepository.findAllByEmail(email).orElseThrow(() -> new EmailNotExitedException(email));

        // 비밀번호 틀릴 때 예외
        if(!passwordEncoder.matches(password, user.getPassword())) throw new PasswordWrongException();


        return user;
    }
}
