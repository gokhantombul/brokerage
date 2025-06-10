package com.inghubstr.brokerage.v1.model;

import com.inghubstr.brokerage.v1.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Customer extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private boolean isAdmin;

}
