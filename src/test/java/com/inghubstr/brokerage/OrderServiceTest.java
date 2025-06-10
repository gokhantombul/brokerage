package com.inghubstr.brokerage;

import com.inghubstr.brokerage.v1.dto.OrderDto;
import com.inghubstr.brokerage.v1.mapper.OrderMapper;
import com.inghubstr.brokerage.v1.model.Asset;
import com.inghubstr.brokerage.v1.model.Order;
import com.inghubstr.brokerage.v1.model.enums.OrderSide;
import com.inghubstr.brokerage.v1.model.enums.OrderStatus;
import com.inghubstr.brokerage.v1.model.enums.Status;
import com.inghubstr.brokerage.v1.repository.AssetRepository;
import com.inghubstr.brokerage.v1.repository.OrderRepository;
import com.inghubstr.brokerage.v1.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AssetRepository assetRepository;

    private Asset tryAsset;
    private Asset aselsAsset;
    private Order order;
    private OrderDto orderDto;

    @BeforeEach
    void setup() {
        tryAsset = new Asset(1L, "TRY", new BigDecimal("100000"), new BigDecimal("100000"));
        aselsAsset = new Asset(1L, "ASELS", BigDecimal.valueOf(500), BigDecimal.valueOf(500));

        orderDto = new OrderDto(1L, "ASELS", OrderSide.BUY, new BigDecimal("20"), new BigDecimal("30"), null);

        order = new Order();
        order.setId(1L);
        order.setUserId(1L);
        order.setAssetName("ASELS");
        order.setPrice(new BigDecimal("30"));
        order.setSize(new BigDecimal("20"));
        order.setSide(OrderSide.BUY);
        order.setOrderStatus(OrderStatus.PENDING);
    }

    @Test
    void testCreateBuyOrder_success() {
        OrderDto orderDto = new OrderDto(1L, "TRY", OrderSide.BUY, BigDecimal.valueOf(50), BigDecimal.valueOf(25), null);
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setOrderStatus(OrderStatus.PENDING);

        when(assetRepository.findByAssetNameAndStatus("TRY", Status.ACTIVE))
                .thenReturn(Optional.of(tryAsset));

        when(assetRepository.save(any(Asset.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(orderMapper.toEntity(orderDto)).thenReturn(mockOrder);

        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        Order created = orderService.createOrder(orderDto);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals(OrderStatus.PENDING, created.getOrderStatus());

        verify(assetRepository).findByAssetNameAndStatus("TRY", Status.ACTIVE);
        verify(orderMapper).toEntity(orderDto);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testCreateSellOrder_success() {
        // Arrange
        OrderDto orderDto = new OrderDto(1L, "ASELS", OrderSide.SELL, BigDecimal.valueOf(100), BigDecimal.valueOf(25), null);
        Order mockOrder = new Order();
        mockOrder.setId(2L);
        mockOrder.setOrderStatus(OrderStatus.PENDING);

        BigDecimal expectedUsable = aselsAsset.getUsableSize().subtract(orderDto.getSize());

        // ✅ DOĞRU mock
        when(assetRepository.findByAssetNameAndStatus("ASELS", Status.ACTIVE))
                .thenReturn(Optional.of(aselsAsset));

        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        // ✅ Mapper mock
        when(orderMapper.toEntity(orderDto)).thenReturn(mockOrder);

        // Act
        Order created = orderService.createOrder(orderDto);

        // Assert
        assertNotNull(created);
        assertEquals(2L, created.getId());
        assertEquals(OrderStatus.PENDING, created.getOrderStatus());
        assertEquals(expectedUsable, aselsAsset.getUsableSize());

        // Verify
        verify(assetRepository).findByAssetNameAndStatus("ASELS", Status.ACTIVE);
        verify(assetRepository).save(any(Asset.class));
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testCancelBuyOrder_success() {
        // TRY'den 600 TL düşülmüş gibi simüle et
        tryAsset.setUsableSize(new BigDecimal("99400"));

        // Mocklar
        when(assetRepository.findByAssetNameAndStatus("TRY", Status.ACTIVE))
                .thenReturn(Optional.of(tryAsset));

        when(orderMapper.toEntity(orderDto))
                .thenReturn(order);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(assetRepository.save(any(Asset.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // 1. adım: createOrder (TRY'den 600 TL düşer)
        orderService.createOrder(orderDto);

        // 2. adım: cancelOrder (TRY'ye 600 TL geri eklenir)
        orderService.cancelOrder(1L, 1L);

        // Assert: emir iptal edildi, TRY bakiyesi eski haline döndü
        assertEquals(OrderStatus.CANCELED, order.getOrderStatus());
        assertEquals(new BigDecimal("100000"), tryAsset.getUsableSize());
    }

    @Test
    void testMatchBuyOrder_updatesAsset() {
        OrderDto orderDto = new OrderDto(1L, "ASELS", OrderSide.BUY, BigDecimal.valueOf(10.0), BigDecimal.valueOf(20.0), OrderStatus.PENDING);
        // Mocklar
        when(assetRepository.findByAssetNameAndStatus("TRY", Status.ACTIVE))
                .thenReturn(Optional.of(tryAsset));

        when(orderMapper.toEntity(orderDto))
                .thenReturn(order);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(assetRepository.save(any(Asset.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order created = orderService.createOrder(orderDto);
        orderService.matchOrder(created.getId());

        Order matched = orderRepository.findById(created.getId()).orElseThrow();
        assertEquals(OrderStatus.MATCHED, matched.getOrderStatus());

        Asset asels = assetRepository.findById(1L).orElseThrow();
        assertEquals(BigDecimal.valueOf(510.0), asels.getSize());
        assertEquals(BigDecimal.valueOf(510.0), asels.getUsableSize());
    }

    @Test
    void testMatchSellOrder_updatesTRY() {
        OrderDto orderDto = new OrderDto(1L, "ASELS", OrderSide.SELL, BigDecimal.valueOf(10.0), BigDecimal.valueOf(30.0), OrderStatus.PENDING);
        Order created = orderService.createOrder(orderDto);
        orderService.matchOrder(created.getId());

        Order matched = orderRepository.findById(created.getId()).orElseThrow();
        assertEquals(OrderStatus.MATCHED, matched.getOrderStatus());

        Asset tryAsset = assetRepository.findById(1L).orElseThrow();
        assertEquals(BigDecimal.valueOf(100000.0 + 300.0), tryAsset.getSize());
        assertEquals(BigDecimal.valueOf(100000.0 + 300.0), tryAsset.getUsableSize());
    }
}
