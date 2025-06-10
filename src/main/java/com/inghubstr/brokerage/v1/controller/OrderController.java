package com.inghubstr.brokerage.v1.controller;

import com.inghubstr.brokerage.v1.dto.OrderDto;
import com.inghubstr.brokerage.v1.mapper.OrderMapper;
import com.inghubstr.brokerage.v1.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.toDto(orderService.createOrder(orderDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id, @RequestParam Long customerId) {
        orderService.cancelOrder(id, customerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> listOrders(@RequestParam Long customerId,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(orderMapper.toDto(orderService.listOrders(customerId, start, end)));
    }

    @PostMapping("/match/{orderId}")
    public ResponseEntity<?> matchOrder(@PathVariable Long orderId) {
        orderService.matchOrder(orderId);
        return ResponseEntity.ok().build();
    }

}
