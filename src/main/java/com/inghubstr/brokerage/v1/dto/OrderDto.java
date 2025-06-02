package com.inghubstr.brokerage.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inghubstr.brokerage.v1.dto.base.BaseDto;
import com.inghubstr.brokerage.v1.model.enums.OrderSide;
import com.inghubstr.brokerage.v1.model.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class OrderDto extends BaseDto {

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotBlank(message = "Asset name cannot be blank")
    @Size(min = 2, max = 512, message = "Asset name must be between 2 and 512 characters.")
    private String assetName;

    @NotNull(message = "Side can not be null")
    private OrderSide side;

    @NotNull(message = "Size cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Size must be greater than zero")
    @Digits(integer = 18, fraction = 2, message = "Size must be a valid decimal number with two decimal places")
    private BigDecimal size;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Digits(integer = 18, fraction = 2, message = "Price must be a valid decimal number with two decimal places")
    private BigDecimal price;

    @NotNull(message = "Order Status can not be null")
    private OrderStatus orderStatus;

}
