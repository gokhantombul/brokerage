package com.inghubstr.brokerage.v1.service.user;

import com.inghubstr.brokerage.v1.exception.ApiRequestException;
import com.inghubstr.brokerage.v1.model.user.Role;
import com.inghubstr.brokerage.v1.repository.user.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new ApiRequestException("Rol not found. Name: " + name, HttpStatus.NOT_FOUND));
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public Role createRoleIfNotFound(String name) {
        Role role = findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }

}
