package com.inghubstr.brokerage.v1.model;

import com.inghubstr.brokerage.v1.model.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Asset extends BaseEntity {

    private Long userId;
    private String assetName; // e.g. "AAPL", "TRY"
    private BigDecimal size;
    private BigDecimal usableSize;

}
