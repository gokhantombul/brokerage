package com.inghubstr.brokerage.v1.repository.user;

import com.inghubstr.brokerage.v1.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
