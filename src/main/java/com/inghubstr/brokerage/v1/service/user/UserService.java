package com.inghubstr.brokerage.v1.service.user;

import com.inghubstr.brokerage.v1.dto.user.RoleDTO;
import com.inghubstr.brokerage.v1.dto.user.UserDTO;
import com.inghubstr.brokerage.v1.exception.ApiRequestException;
import com.inghubstr.brokerage.v1.model.user.Role;
import com.inghubstr.brokerage.v1.model.user.User;
import com.inghubstr.brokerage.v1.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDTO findByDTOEmailAndDurum(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiRequestException("User not found. email: {}" + email, HttpStatus.NOT_FOUND));
        return userToUserDTO(user, null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<RoleDTO> roleListToRoleDTOList(Collection<Role> roleList) {
        List<RoleDTO> roleDTOList = new ArrayList<>();

        for (Role role : roleList) {
            roleDTOList.add(RoleDTO.builder()
                    .name(role.getName())
                    .build());
        }

        return roleDTOList;
    }

    public UserDTO userToUserDTO(User user, String token) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .tokenExpired(user.isTokenExpired())
                .token(token)
                .roleDTOList(roleListToRoleDTOList(user.getRoleList()))
                .build();
    }

}
