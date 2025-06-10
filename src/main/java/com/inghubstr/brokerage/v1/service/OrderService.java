package com.inghubstr.brokerage.v1.service;

import com.inghubstr.brokerage.v1.dto.OrderDto;
import com.inghubstr.brokerage.v1.exception.ApiRequestException;
import com.inghubstr.brokerage.v1.mapper.OrderMapper;
import com.inghubstr.brokerage.v1.model.Asset;
import com.inghubstr.brokerage.v1.model.Order;
import com.inghubstr.brokerage.v1.model.enums.OrderSide;
import com.inghubstr.brokerage.v1.model.enums.OrderStatus;
import com.inghubstr.brokerage.v1.model.enums.Status;
import com.inghubstr.brokerage.v1.repository.AssetRepository;
import com.inghubstr.brokerage.v1.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AssetRepository assetRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public Order createOrder(OrderDto orderDto) {
        String symbol = orderDto.getSide() == OrderSide.BUY ? "TRY" : orderDto.getAssetName();

        Asset asset = assetRepository.findByAssetNameAndStatus(symbol, Status.ACTIVE)
                .orElseThrow(() -> new ApiRequestException("Asset can not be found"));

        switch (orderDto.getSide()) {
            case BUY -> {
                BigDecimal requiredAmount = orderDto.getPrice().multiply(orderDto.getSize());
                if (asset.getUsableSize().compareTo(requiredAmount) < 0) {
                    throw new ApiRequestException("Insufficient TRY balance");
                }
                asset.setUsableSize(asset.getUsableSize().subtract(requiredAmount));
                assetRepository.save(asset);
            }
            case SELL -> {
                if (asset.getUsableSize().compareTo(orderDto.getSize()) < 0) {
                    throw new ApiRequestException("Insufficient asset balance");
                }
                asset.setUsableSize(asset.getUsableSize().subtract(orderDto.getSize()));
                assetRepository.save(asset);
            }
        }

        orderDto.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(orderMapper.toEntity(orderDto));
    }

    @Transactional
    public void cancelOrder(Long orderId, Long customerId) {
        Order order = orderRepository.findByIdAndStatus(orderId, OrderStatus.PENDING)
                .orElseThrow(() -> new ApiRequestException("Order not found"));

        if (!order.getUserId().equals(customerId)) {
            throw new ApiRequestException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        switch (order.getSide()) {
            case BUY -> {
                buy(order);
            }
            case SELL -> {
                sell(order);
            }
        }

        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    private void buy(Order order) {
        BigDecimal refundAmount = order.getPrice().multiply(order.getSize());
        Asset tryAsset = assetRepository.findByUserIdAndAssetName(order.getUserId(), "TRY")
                .orElseThrow(() -> new ApiRequestException("TRY asset not found"));
        tryAsset.setUsableSize(tryAsset.getUsableSize().add(refundAmount));
        assetRepository.save(tryAsset);
    }

    private void sell(Order order) {
        Asset asset = assetRepository.findByUserIdAndAssetName(order.getUserId(), order.getAssetName())
                .orElseThrow(() -> new ApiRequestException("Asset not found"));
        asset.setUsableSize(asset.getUsableSize().add(order.getSize()));
        assetRepository.save(asset);
    }

    public List<Order> listOrders(Long customerId, LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByUserIdAndCreatedDateBetween(customerId, start, end);
    }

    public void matchOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiRequestException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new ApiRequestException("Only PENDING orders can be matched");
        }

        switch (order.getSide()) {
            case BUY -> {
                Asset asset = assetRepository.findByUserIdAndAssetName(order.getUserId(), order.getAssetName())
                        .orElse(new Asset());
                asset.setUserId(order.getUserId());
                asset.setAssetName(order.getAssetName());
                asset.setSize(asset.getSize() == null ? order.getSize() : asset.getSize().add(order.getSize()));
                asset.setUsableSize(asset.getUsableSize() == null ? order.getSize() : asset.getUsableSize().add(order.getSize()));
                assetRepository.save(asset);
            }
            case SELL -> {
                BigDecimal returnAmount = order.getPrice().multiply(order.getSize());
                Asset tryAsset = assetRepository.findByUserIdAndAssetName(order.getUserId(), "TRY")
                        .orElseThrow(() -> new ApiRequestException("TRY asset not found"));
                tryAsset.setSize(tryAsset.getSize().add(returnAmount));
                tryAsset.setUsableSize(tryAsset.getUsableSize().add(returnAmount));
                assetRepository.save(tryAsset);
            }
        }

        order.setOrderStatus(OrderStatus.MATCHED);
        orderRepository.save(order);
    }

}
