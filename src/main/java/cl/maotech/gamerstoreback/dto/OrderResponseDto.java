package cl.maotech.gamerstoreback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private String orderNumber;
    private LocalDateTime timestamp;
    private CustomerInfo customerInfo;
    private ShippingAddress shippingAddress;
    private List<OrderItemInfo> items;
    private OrderSummary summary;
    private String status;
    private String message;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerInfo {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShippingAddress {
        private String address;
        private String city;
        private String state;
        private String zipCode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemInfo {
        private String id;
        private String name;
        private Integer price;
        private Integer quantity;
        private Integer subtotal;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderSummary {
        private Integer subtotal;
        private Integer shipping;
        private Integer total;
    }
}