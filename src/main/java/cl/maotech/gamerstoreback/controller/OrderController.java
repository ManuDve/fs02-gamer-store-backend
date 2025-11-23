package cl.maotech.gamerstoreback.controller;

import cl.maotech.gamerstoreback.constant.SwaggerMessages;
import cl.maotech.gamerstoreback.dto.OrderRequestDto;
import cl.maotech.gamerstoreback.dto.OrderResponseDto;
import cl.maotech.gamerstoreback.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = SwaggerMessages.Order.TAG_NAME, description = SwaggerMessages.Order.TAG_DESCRIPTION)
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = SwaggerMessages.Order.CREATE_SUMMARY,
            description = SwaggerMessages.Order.CREATE_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = SwaggerMessages.Response.CREATED_201),
            @ApiResponse(responseCode = "400", description = SwaggerMessages.Response.BAD_REQUEST_400),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401)
    })
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderRequestDto orderRequest,
            Authentication authentication) {
        String userEmail = authentication.getName();
        OrderResponseDto response = orderService.createOrder(orderRequest, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = SwaggerMessages.Order.GET_USER_ORDERS_SUMMARY,
            description = SwaggerMessages.Order.GET_USER_ORDERS_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401)
    })
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(Authentication authentication) {
        String userEmail = authentication.getName();
        List<OrderResponseDto> orders = orderService.getOrdersByUser(userEmail);
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = SwaggerMessages.Order.GET_ALL_ORDERS_SUMMARY,
            description = SwaggerMessages.Order.GET_ALL_ORDERS_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403)
    })
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersAdmin() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = SwaggerMessages.Order.GET_BY_NUMBER_SUMMARY,
            description = SwaggerMessages.Order.GET_BY_NUMBER_DESCRIPTION,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SwaggerMessages.Response.SUCCESS_200),
            @ApiResponse(responseCode = "401", description = SwaggerMessages.Response.UNAUTHORIZED_401),
            @ApiResponse(responseCode = "403", description = SwaggerMessages.Response.FORBIDDEN_403),
            @ApiResponse(responseCode = "404", description = SwaggerMessages.Response.NOT_FOUND_404)
    })
    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderResponseDto> getOrderByNumber(
            @Parameter(description = SwaggerMessages.Order.ORDER_NUMBER_PARAM_DESCRIPTION, example = SwaggerMessages.Order.ORDER_NUMBER_PARAM_EXAMPLE) @PathVariable String orderNumber,
            Authentication authentication) {
        String userEmail = authentication.getName();
        OrderResponseDto order = orderService.getOrderByNumber(orderNumber, userEmail);
        return ResponseEntity.ok(order);
    }
}
