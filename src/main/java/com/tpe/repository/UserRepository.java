package com.tpe.repository;

import com.tpe.domain.User;
import com.tpe.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName) throws ResourceNotFoundException;
    // UserService gibi bir clasim olmayacagi icin ( UserDetailService ) exception kismini burada handle ettik.
}
