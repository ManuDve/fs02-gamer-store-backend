package cl.maotech.gamerstoreback.controller;

import cl.maotech.gamerstoreback.dto.OrderRequestDto;
import cl.maotech.gamerstoreback.dto.OrderResponseDto;
import cl.maotech.gamerstoreback.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderRequestDto orderRequest,
            Authentication authentication) {
        String userEmail = authentication.getName();
        OrderResponseDto response = orderService.createOrder(orderRequest, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

