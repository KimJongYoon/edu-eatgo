package kr.co.fastcompus.eatgo.application;

import kr.co.fastcompus.eatgo.domain.ResourceNotFoundException;
import kr.co.fastcompus.eatgo.domain.User;
import kr.co.fastcompus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(String email, String name) {
        User user = User.builder().email(email).name(name).level(1L).build();
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, String email, String name, Long level) {
        User user = userRepository.findAllById(id).orElseThrow(()->new ResourceNotFoundException("Could not find userId " + id));
        user.updateUser(email, name, level);
        return user;
    }

    @Transactional
    public User deactivateUser(Long id) {
        User user = userRepository.findAllById(id).orElseThrow(()->new ResourceNotFoundException("Could not find userId " + id));

        user.deactivate();

        return user;
    }
}
