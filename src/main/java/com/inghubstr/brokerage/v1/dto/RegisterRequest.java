package com.inghubstr.brokerage.v1.dto;

import com.inghubstr.brokerage.v1.model.user.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 512, message = "The name can be up to 512 characters.")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    @Size(max = 512, message = "The surname can be up to 512 characters.")
    private String surname;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 512, message = "The email can be up to 512 characters.")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(max = 10, message = "The password can be up to 10 characters.")
    private String password;

    private List<UUID> roleUuidList;

    private List<Role> roleList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(roleUuidList, that.roleUuidList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, password, roleUuidList);
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleUuidList=" + roleUuidList +
                ", roleList=" + roleList +
                '}';
    }

}
