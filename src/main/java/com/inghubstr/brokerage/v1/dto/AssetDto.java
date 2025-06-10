package com.inghubstr.brokerage.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inghubstr.brokerage.v1.dto.base.BaseDto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
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
public class AssetDto extends BaseDto {

    private Long userId;

    private String assetName;

    @Digits(integer = 18, fraction = 2, message = "Size must be a valid decimal number with two decimal places")
    @DecimalMin(value = "0", inclusive = false, message = "The current size must be greater than 0")
    private BigDecimal size;

    @Digits(integer = 18, fraction = 2, message = "Usable Size must be a valid decimal number with two decimal places")
    private BigDecimal usableSize;

}
