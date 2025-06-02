package com.inghubstr.brokerage.v1.mapper;

import com.inghubstr.brokerage.v1.dto.OrderDto;
import com.inghubstr.brokerage.v1.mapper.base.BaseMapper;
import com.inghubstr.brokerage.v1.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<Order, OrderDto> {

}
