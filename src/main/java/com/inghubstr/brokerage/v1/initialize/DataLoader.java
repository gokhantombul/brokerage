package com.inghubstr.brokerage.v1.initialize;

import com.inghubstr.brokerage.v1.dto.RegisterRequest;
import com.inghubstr.brokerage.v1.model.Asset;
import com.inghubstr.brokerage.v1.model.Order;
import com.inghubstr.brokerage.v1.model.enums.OrderSide;
import com.inghubstr.brokerage.v1.model.enums.OrderStatus;
import com.inghubstr.brokerage.v1.model.user.Role;
import com.inghubstr.brokerage.v1.repository.AssetRepository;
import com.inghubstr.brokerage.v1.repository.OrderRepository;
import com.inghubstr.brokerage.v1.service.user.AuthenticationService;
import com.inghubstr.brokerage.v1.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final RoleService roleService;
    private final AssetRepository assetRepository;
    private final OrderRepository orderRepository;
    private final AuthenticationService authenticationService;

    @Override
    public void run(ApplicationArguments args) {
        createRoles();
        createUser();
        createAssets();
        createOrder();
    }

    private void createUser() {
        authenticationService.register(RegisterRequest.builder()
                .name("Gökhan")
                .surname("Tombul")
                .email("gokhantombul@hotmail.com")
                .password("1234")
                .roleList(Arrays.asList(roleService.findByName("ROLE_ADMIN"), roleService.findByName("ROLE_USER")))
                .build());

        authenticationService.register(RegisterRequest.builder()
                .name("Alice")
                .surname("Wonderland")
                .email("alicewonderland@hotmail.com")
                .password("pass1")
                .roleList(Collections.singletonList(roleService.findByName("ROLE_USER")))
                .build());

        authenticationService.register(RegisterRequest.builder()
                .name("Bob")
                .surname("Marley")
                .email("bob.marley@gmail.com")
                .password("pass2")
                .roleList(Collections.singletonList(roleService.findByName("ROLE_USER")))
                .build());

        authenticationService.register(RegisterRequest.builder()
                .name("John")
                .surname("Doe")
                .email("johndoe@yandex.com")
                .password("pass3")
                .roleList(Collections.singletonList(roleService.findByName("ROLE_USER")))
                .build());
    }

    private void createRoles() {
        roleService.save(new Role("ROLE_ADMIN"));
        roleService.save(new Role("ROLE_USER"));
    }

    private void createAssets() {
        assetRepository.save(new Asset(1L, "TRY", BigDecimal.valueOf(100000.0), BigDecimal.valueOf(100000.0)));
        assetRepository.save(new Asset(1L, "ASELS", BigDecimal.valueOf(500.0), BigDecimal.valueOf(500.0)));
        assetRepository.save(new Asset(2L, "TRY", BigDecimal.valueOf(50000.0), BigDecimal.valueOf(50000.0)));
        assetRepository.save(new Asset(3L, "TRY", BigDecimal.valueOf(75000.0), BigDecimal.valueOf(75000.0)));
        assetRepository.save(new Asset(4L, "THYAO", BigDecimal.valueOf(120.0), BigDecimal.valueOf(120.0)));
    }

    private void createOrder() {
        orderRepository.save(new Order(1L, "ASELS", OrderSide.BUY, BigDecimal.valueOf(50.0), BigDecimal.valueOf(26.0), OrderStatus.PENDING));
        orderRepository.save(new Order(1L, "ASELS", OrderSide.SELL, BigDecimal.valueOf(30.0), BigDecimal.valueOf(27.5), OrderStatus.PENDING));
        orderRepository.save(new Order(2L, "ASELS", OrderSide.BUY, BigDecimal.valueOf(100.0), BigDecimal.valueOf(24.0), OrderStatus.PENDING));
        orderRepository.save(new Order(3L, "ASELS", OrderSide.BUY, BigDecimal.valueOf(75.0), BigDecimal.valueOf(25.0), OrderStatus.PENDING));
        orderRepository.save(new Order(4L, "THYAO", OrderSide.SELL, BigDecimal.valueOf(50.0), BigDecimal.valueOf(100.0), OrderStatus.PENDING));
    }

}
