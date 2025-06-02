package com.inghubstr.brokerage.v1.model.base;

import com.inghubstr.brokerage.v1.util.ProjectHelper;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseEntityListener {

    @PrePersist
    public void create(BaseEntity entity) {
        entity.setUuid(UUID.randomUUID());
        entity.setCreatedDate(LocalDateTime.now());

        if (entity.getCreatedBy() == null || entity.getCreatedBy() != 0) {
            entity.setCreatedBy(ProjectHelper.getLoggedInUserId());
        }
    }

    @PreUpdate
    public void update(BaseEntity entity) {
        entity.setLastModifiedDate(LocalDateTime.now());
        if (entity.getLastModifiedBy() == null || entity.getLastModifiedBy() != 0) {
            entity.setLastModifiedBy(ProjectHelper.getLoggedInUserId());
        }
    }

}
