package com.inghubstr.brokerage.v1.repository.user;

import com.inghubstr.brokerage.v1.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    Optional<Role> findByUuid(UUID roleUuid);

}
