package kr.co.fastcompus.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    Optional<User> findAllByEmail(String email);

    Optional<User> findAllById(Long id);
}
