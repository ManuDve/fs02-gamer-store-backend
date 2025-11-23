package cl.maotech.gamerstoreback.service;

import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.constant.SecurityRoles;
import cl.maotech.gamerstoreback.dto.OrderRequestDto;
import cl.maotech.gamerstoreback.dto.OrderResponseDto;
import cl.maotech.gamerstoreback.exception.BusinessValidationException;
import cl.maotech.gamerstoreback.exception.ResourceNotFoundException;
import cl.maotech.gamerstoreback.model.*;
import cl.maotech.gamerstoreback.repository.OrderRepository;
import cl.maotech.gamerstoreback.repository.ProductRepository;
import cl.maotech.gamerstoreback.repository.ProductStockRepository;
import cl.maotech.gamerstoreback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequest, String userEmail) {
        OrderRequestDto.OrderData orderData = orderRequest.getOrder();

        // Buscar el usuario autenticado
        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.User.USERNAME_NOT_FOUND + userEmail));

        // Validar productos y stock
        int calculatedSubtotal = 0;
        List<OrderItemData> orderItemsData = new ArrayList<>();

        for (OrderRequestDto.OrderItemDto item : orderData.getItems()) {
            // Verificar que el producto existe
            Product product = productRepository.findById(item.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(Messages.Product.NOT_FOUND + item.getId()));

            // Verificar que tiene stock
            if (product.getStock() == null) {
                throw new BusinessValidationException(Messages.Product.NO_STOCK + product.getName());
            }

            // Verificar que hay suficiente stock
            if (product.getStock().getQuantity() < item.getQuantity()) {
                throw new BusinessValidationException(
                    Messages.Product.INSUFFICIENT_STOCK + product.getName() +
                    Messages.Product.STOCK_AVAILABLE + product.getStock().getQuantity() +
                    Messages.Product.STOCK_REQUESTED + item.getQuantity()
                );
            }

            // Calcular subtotal con el precio actual del producto
            int itemSubtotal = product.getPrice() * item.getQuantity();
            calculatedSubtotal += itemSubtotal;

            orderItemsData.add(new OrderItemData(product, item.getQuantity(), product.getPrice()));
        }

        // Calcular total
        int calculatedTotal = calculatedSubtotal + orderData.getShipping();

        // Generar número de orden único
        String orderNumber = generateOrderNumber();

        // Crear la orden usando la información del usuario autenticado
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setTimestamp(LocalDateTime.now());
        order.setUser(user);
        order.setFirstName(user.getName().split(" ")[0]);
        order.setLastName(user.getName().contains(" ") ? user.getName().substring(user.getName().indexOf(" ") + 1) : "");
        order.setEmail(user.getUsername());
        order.setPhone(user.getPhone());
        order.setAddress(orderData.getShippingAddress().getAddress());
        order.setCity(orderData.getShippingAddress().getCity());
        order.setState(orderData.getShippingAddress().getState());
        order.setZipCode(orderData.getShippingAddress().getZipCode());
        order.setSubtotal(calculatedSubtotal);
        order.setShipping(orderData.getShipping());
        order.setTotal(calculatedTotal);

        // Agregar items y actualizar stock
        for (OrderItemData itemData : orderItemsData) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(itemData.product.getId());
            orderItem.setProductName(itemData.product.getName());
            orderItem.setPrice(itemData.price);
            orderItem.setQuantity(itemData.quantity);
            order.addItem(orderItem);

            // Actualizar stock
            ProductStock stock = itemData.product.getStock();
            stock.setQuantity(stock.getQuantity() - itemData.quantity);
            productStockRepository.save(stock);
        }

        // Guardar la orden
        Order savedOrder = orderRepository.save(order);

        // Construir respuesta
        return buildOrderResponse(savedOrder);
    }

    public List<OrderResponseDto> getOrdersByUser(String userEmail) {
        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.User.USERNAME_NOT_FOUND + userEmail));

        List<Order> orders = orderRepository.findByUserOrderByTimestampDesc(user);
        return orders.stream()
                .map(this::buildOrderResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAllByOrderByTimestampDesc();
        return orders.stream()
                .map(this::buildOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponseDto getOrderByNumber(String orderNumber, String userEmail) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.Order.NOT_FOUND + orderNumber));

        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.User.USERNAME_NOT_FOUND + userEmail));

        // Verificar que el usuario sea el dueño de la orden o sea admin
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> SecurityRoles.ROLE_ADMIN.equals(auth.getAuthority()));

        if (!order.getUser().getId().equals(user.getId()) && !isAdmin) {
            throw new BusinessValidationException(Messages.Order.UNAUTHORIZED_ACCESS);
        }

        return buildOrderResponse(order);
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private OrderResponseDto buildOrderResponse(Order order) {
        OrderResponseDto response = new OrderResponseDto();
        response.setOrderNumber(order.getOrderNumber());
        response.setTimestamp(order.getTimestamp());
        response.setStatus(Messages.Order.STATUS_COMPLETED);
        response.setMessage(Messages.Order.CREATED);

        OrderResponseDto.CustomerInfo customerInfo = new OrderResponseDto.CustomerInfo();
        customerInfo.setFirstName(order.getFirstName());
        customerInfo.setLastName(order.getLastName());
        customerInfo.setEmail(order.getEmail());
        customerInfo.setPhone(order.getPhone());
        response.setCustomerInfo(customerInfo);

        OrderResponseDto.ShippingAddress shippingAddress = new OrderResponseDto.ShippingAddress();
        shippingAddress.setAddress(order.getAddress());
        shippingAddress.setCity(order.getCity());
        shippingAddress.setState(order.getState());
        shippingAddress.setZipCode(order.getZipCode());
        response.setShippingAddress(shippingAddress);

        List<OrderResponseDto.OrderItemInfo> items = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            OrderResponseDto.OrderItemInfo itemInfo = new OrderResponseDto.OrderItemInfo();
            itemInfo.setId(item.getProductId());
            itemInfo.setName(item.getProductName());
            itemInfo.setPrice(item.getPrice());
            itemInfo.setQuantity(item.getQuantity());
            itemInfo.setSubtotal(item.getPrice() * item.getQuantity());
            items.add(itemInfo);
        }
        response.setItems(items);

        OrderResponseDto.OrderSummary summary = new OrderResponseDto.OrderSummary();
        summary.setSubtotal(order.getSubtotal());
        summary.setShipping(order.getShipping());
        summary.setTotal(order.getTotal());
        response.setSummary(summary);

        return response;
    }

    private static class OrderItemData {
        Product product;
        int quantity;
        int price;

        OrderItemData(Product product, int quantity, int price) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
        }
    }
}
