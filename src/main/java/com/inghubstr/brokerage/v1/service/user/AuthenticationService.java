package com.inghubstr.brokerage.v1.service.user;

import com.inghubstr.brokerage.v1.config.JwtService;
import com.inghubstr.brokerage.v1.dto.RegisterRequest;
import com.inghubstr.brokerage.v1.dto.UserRequest;
import com.inghubstr.brokerage.v1.dto.user.UserDTO;
import com.inghubstr.brokerage.v1.exception.ApiRequestException;
import com.inghubstr.brokerage.v1.model.user.Role;
import com.inghubstr.brokerage.v1.model.user.User;
import com.inghubstr.brokerage.v1.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserDTO register(RegisterRequest request) {
        List<Role> atanacakRolList = new ArrayList<>();

        if (request.getRoleUuidList() != null && !request.getRoleUuidList().isEmpty()) {
            for (UUID roleUuid : request.getRoleUuidList()) {
                Role role = roleRepository.findByUuid(roleUuid).orElseThrow(() -> new ApiRequestException("Role not found. uuid: " + roleUuid, HttpStatus.NOT_FOUND));
                atanacakRolList.add(role);
            }
        } else if (request.getRoleList() != null && !request.getRoleList().isEmpty()) {
            atanacakRolList = request.getRoleList();
        }

        var user = User.builder()
                .firstName(request.getName())
                .lastName(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleList(atanacakRolList)
                .tokenExpired(true)
                .build();
        userService.save(user);
        return userService.userToUserDTO(user, null);
    }

    public UserDTO authenticate(UserRequest request) {
        var user = userService.findByEmail(request.getEmail()).orElseThrow(() -> new ApiRequestException("Kullanıcı ile giriş yapılamadı. email: {}", request.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR));
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtService.createToken(authentication);
        return userService.userToUserDTO(user, token);
    }

}
