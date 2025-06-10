package com.inghubstr.brokerage.v1.model;

import com.inghubstr.brokerage.v1.model.base.BaseEntity;
import com.inghubstr.brokerage.v1.model.enums.OrderSide;
import com.inghubstr.brokerage.v1.model.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "Orders")
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends BaseEntity {

    private Long userId;

    private String assetName;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    private BigDecimal size;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
