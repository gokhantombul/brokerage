package com.inghubstr.brokerage.v1.mapper;

import com.inghubstr.brokerage.v1.dto.OrderDto;
import com.inghubstr.brokerage.v1.model.Order;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T14:44:33+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toEntity(OrderDto dto) {
        if ( dto == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.assetName( dto.getAssetName() );
        order.side( dto.getSide() );
        order.size( dto.getSize() );
        order.price( dto.getPrice() );
        order.orderStatus( dto.getOrderStatus() );

        return order.build();
    }

    @Override
    public List<Order> toEntity(List<OrderDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<Order> list = new ArrayList<Order>( dto.size() );
        for ( OrderDto orderDto : dto ) {
            list.add( toEntity( orderDto ) );
        }

        return list;
    }

    @Override
    public OrderDto toDto(Order entity) {
        if ( entity == null ) {
            return null;
        }

        OrderDto.OrderDtoBuilder<?, ?> orderDto = OrderDto.builder();

        orderDto.uuid( entity.getUuid() );
        orderDto.createdDate( entity.getCreatedDate() );
        orderDto.lastModifiedDate( entity.getLastModifiedDate() );
        orderDto.createdBy( entity.getCreatedBy() );
        orderDto.lastModifiedBy( entity.getLastModifiedBy() );
        orderDto.status( entity.getStatus() );
        orderDto.assetName( entity.getAssetName() );
        orderDto.side( entity.getSide() );
        orderDto.size( entity.getSize() );
        orderDto.price( entity.getPrice() );
        orderDto.orderStatus( entity.getOrderStatus() );

        return orderDto.build();
    }

    @Override
    public List<OrderDto> toDto(List<Order> entity) {
        if ( entity == null ) {
            return null;
        }

        List<OrderDto> list = new ArrayList<OrderDto>( entity.size() );
        for ( Order order : entity ) {
            list.add( toDto( order ) );
        }

        return list;
    }
}
