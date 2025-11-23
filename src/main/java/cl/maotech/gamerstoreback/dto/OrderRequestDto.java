package cl.maotech.gamerstoreback.dto;

import cl.maotech.gamerstoreback.constant.Messages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = Messages.Validation.ORDER_NULL)
    @Valid
    private OrderData order;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderData {
        @NotNull(message = Messages.Validation.SHIPPING_ADDRESS_REQUIRED)
        @Valid
        private ShippingAddress shippingAddress;

        @NotNull(message = Messages.Validation.ITEMS_REQUIRED)
        @NotEmpty(message = Messages.Validation.ITEMS_NOT_EMPTY)
        @Valid
        private List<OrderItemDto> items;

        @NotNull(message = Messages.Validation.PAYMENT_REQUIRED)
        @Valid
        private PaymentInfo payment;

        @NotNull(message = Messages.Validation.SHIPPING_COST_REQUIRED)
        @Min(value = 0, message = Messages.Validation.SHIPPING_COST_NEGATIVE)
        private Integer shipping;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShippingAddress {
        @NotBlank(message = Messages.Validation.ADDRESS_REQUIRED)
        private String address;

        @NotBlank(message = Messages.Validation.CITY_REQUIRED)
        private String city;

        @NotBlank(message = Messages.Validation.STATE_REQUIRED)
        private String state;

        @NotBlank(message = Messages.Validation.ZIP_CODE_REQUIRED)
        private String zipCode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDto {
        @NotBlank(message = Messages.Validation.PRODUCT_ID_REQUIRED)
        private String id;

        @NotNull(message = Messages.Validation.QUANTITY_REQUIRED)
        @Min(value = 1, message = Messages.Validation.QUANTITY_MIN)
        private Integer quantity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentInfo {
        @NotBlank(message = Messages.Validation.CARD_NUMBER_REQUIRED)
        private String cardNumber;

        @NotBlank(message = Messages.Validation.CARD_NAME_REQUIRED)
        private String cardName;

        @NotBlank(message = Messages.Validation.CARD_EXPIRY_REQUIRED)
        private String cardExpiry;

        @NotBlank(message = Messages.Validation.CARD_CVV_REQUIRED)
        private String cardCVV;
    }
}
