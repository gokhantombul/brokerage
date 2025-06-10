package com.inghubstr.brokerage.v1.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.inghubstr.brokerage.v1.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
public class Role extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "roleList")
    @JsonManagedReference
    private Collection<User> userList;

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }

}