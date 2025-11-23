package cl.maotech.gamerstoreback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private OrderData order;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderData {
        private String orderNumber;
        private String timestamp;
        private CustomerInfo customerInfo;
        private ShippingAddress shippingAddress;
        private List<OrderItemDto> items;
        private OrderSummary summary;
        private PaymentInfo payment;
    }

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
    public static class OrderItemDto {
        private String id;
        private String name;
        private Integer price;
        private Integer quantity;
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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentInfo {
        private String cardNumber;
        private String cardName;
        private String cardExpiry;
        private String cardCVV;
    }
}

