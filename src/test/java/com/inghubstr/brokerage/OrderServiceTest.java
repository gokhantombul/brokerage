package com.inghubstr.brokerage;

import com.inghubstr.brokerage.v1.dto.OrderDto;
import com.inghubstr.brokerage.v1.model.Asset;
import com.inghubstr.brokerage.v1.model.Order;
import com.inghubstr.brokerage.v1.model.enums.OrderSide;
import com.inghubstr.brokerage.v1.model.enums.OrderStatus;
import com.inghubstr.brokerage.v1.repository.AssetRepository;
import com.inghubstr.brokerage.v1.repository.OrderRepository;
import com.inghubstr.brokerage.v1.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureTestDatabase
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AssetRepository assetRepository;

    @BeforeEach
    void setup() {
        // Clear all previous data
        orderRepository.deleteAll();
        assetRepository.deleteAll();

        assetRepository.save(new Asset(1L, "TRY", BigDecimal.valueOf(100000.0), BigDecimal.valueOf(100000.0)));
        assetRepository.save(new Asset(1L, "ASELS", BigDecimal.valueOf(500.0), BigDecimal.valueOf(500.0)));
        assetRepository.save(new Asset(2L, "TRY", BigDecimal.valueOf(50000.0), BigDecimal.valueOf(50000.0)));
        assetRepository.save(new Asset(3L, "TRY", BigDecimal.valueOf(75000.0), BigDecimal.valueOf(75000.0)));
        assetRepository.save(new Asset(4L, "THYAO", BigDecimal.valueOf(120.0), BigDecimal.valueOf(120.0)));
    }

    @Test
    void testCreateBuyOrder_success() {
        OrderDto orderDto = new OrderDto(1L, "ASELS", OrderSide.BUY, BigDecimal.valueOf(50.0), BigDecimal.valueOf(25.0), OrderStatus.PENDING);
        Order created = orderService.createOrder(orderDto);

        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals(OrderStatus.PENDING, created.getOrderStatus());

        Asset tryAsset = assetRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(BigDecimal.valueOf(100000.0 - (50.0 * 25.0)), tryAsset.getUsableSize());
    }

    @Test
    void testCreateSellOrder_success() {
        OrderDto orderDto = new OrderDto(1L, "ASELS", OrderSide.SELL, BigDecimal.valueOf(100.0), BigDecimal.valueOf(25.0), OrderStatus.PENDING);
        Order created = orderService.createOrder(orderDto);

        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals(OrderStatus.PENDING, created.getOrderStatus());

        Asset aselsAsset = assetRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(BigDecimal.valueOf(400.0), aselsAsset.getUsableSize());
    }

    @Test
    void testCancelBuyOrder_success() {
        // Setup TRY asset
        Asset tryAsset = assetRepository.findById(1L).orElseThrow();
        BigDecimal initialUsable = tryAsset.getUsableSize();

        // Create and cancel buy order
        OrderDto orderDto = new OrderDto(1L, "ASELS", OrderSide.BUY, BigDecimal.valueOf(20.0), BigDecimal.valueOf(30.0), OrderStatus.PENDING); // 600 TL'lik alım
        Order created = orderService.createOrder(orderDto);
        orderService.cancelOrder(created.getId(), 1L);

        // Check order status
        Order cancelled = orderRepository.findById(created.getId()).orElseThrow();
        Assertions.assertEquals(OrderStatus.CANCELED, cancelled.getStatus());

        // Check TRY usable amount returned correctly
        Asset updatedTry = assetRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(initialUsable, updatedTry.getUsableSize());
    }

    @Test
    void testMatchBuyOrder_updatesAsset() {
        OrderDto orderDto = new OrderDto(1L, "ASELS", OrderSide.BUY, BigDecimal.valueOf(10.0), BigDecimal.valueOf(20.0), OrderStatus.PENDING);
        Order created = orderService.createOrder(orderDto);
        orderService.matchOrder(created.getId());

        Order matched = orderRepository.findById(created.getId()).orElseThrow();
        Assertions.assertEquals(OrderStatus.MATCHED, matched.getOrderStatus());

        Asset asels = assetRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(BigDecimal.valueOf(510.0), asels.getSize());
        Assertions.assertEquals(BigDecimal.valueOf(510.0), asels.getUsableSize());
    }

    @Test
    void testMatchSellOrder_updatesTRY() {
        OrderDto orderDto = new OrderDto(1L, "ASELS", OrderSide.SELL, BigDecimal.valueOf(10.0), BigDecimal.valueOf(30.0), OrderStatus.PENDING);
        Order created = orderService.createOrder(orderDto);
        orderService.matchOrder(created.getId());

        Order matched = orderRepository.findById(created.getId()).orElseThrow();
        Assertions.assertEquals(OrderStatus.MATCHED, matched.getOrderStatus());

        Asset tryAsset = assetRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(BigDecimal.valueOf(100000.0 + 300.0), tryAsset.getSize());
        Assertions.assertEquals(BigDecimal.valueOf(100000.0 + 300.0), tryAsset.getUsableSize());
    }
}
