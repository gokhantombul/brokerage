package com.inghubstr.brokerage.v1.dto.user;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private boolean tokenExpired;
    private Collection<RoleDTO> roleDTOList;

}
