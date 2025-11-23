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

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(Authentication authentication) {
        String userEmail = authentication.getName();
        List<OrderResponseDto> orders = orderService.getOrdersByUser(userEmail);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersAdmin() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderResponseDto> getOrderByNumber(
            @PathVariable String orderNumber,
            Authentication authentication) {
        String userEmail = authentication.getName();
        OrderResponseDto order = orderService.getOrderByNumber(orderNumber, userEmail);
        return ResponseEntity.ok(order);
    }
}
