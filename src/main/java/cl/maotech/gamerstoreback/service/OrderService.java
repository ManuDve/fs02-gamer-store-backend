package cl.maotech.gamerstoreback.service;

import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.dto.OrderRequestDto;
import cl.maotech.gamerstoreback.dto.OrderResponseDto;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

        // Validar que la orden tenga items
        if (orderData.getItems() == null || orderData.getItems().isEmpty()) {
            throw new IllegalArgumentException(Messages.Order.EMPTY_ITEMS);
        }

        // Buscar el usuario
        User user = userRepository.findByUsername(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.User.USERNAME_NOT_FOUND + userEmail));

        // Validar productos, stock y calcular total
        int calculatedSubtotal = 0;
        List<Product> productsToValidate = new ArrayList<>();

        for (OrderRequestDto.OrderItemDto item : orderData.getItems()) {
            // Verificar que el producto existe
            Product product = productRepository.findById(item.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(Messages.Product.NOT_FOUND + item.getId()));

            // Verificar que tiene stock
            if (product.getStock() == null) {
                throw new IllegalArgumentException(Messages.Product.NO_STOCK + item.getId());
            }

            // Verificar que hay suficiente stock
            if (product.getStock().getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException(Messages.Product.INSUFFICIENT_STOCK + product.getName() +
                    ". Disponible: " + product.getStock().getQuantity() + ", Solicitado: " + item.getQuantity());
            }

            // Verificar que el precio coincide
            if (!product.getPrice().equals(item.getPrice())) {
                throw new IllegalArgumentException("El precio del producto " + product.getName() +
                    " no coincide. Esperado: " + product.getPrice() + ", Recibido: " + item.getPrice());
            }

            productsToValidate.add(product);
            calculatedSubtotal += item.getPrice() * item.getQuantity();
        }

        // Validar que el total calculado coincide
        int calculatedTotal = calculatedSubtotal + orderData.getSummary().getShipping();
        if (calculatedTotal != orderData.getSummary().getTotal()) {
            throw new IllegalArgumentException(Messages.Order.INVALID_TOTAL +
                ". Calculado: " + calculatedTotal + ", Recibido: " + orderData.getSummary().getTotal());
        }

        // Validar que el subtotal coincide
        if (calculatedSubtotal != orderData.getSummary().getSubtotal()) {
            throw new IllegalArgumentException("El subtotal no coincide. Calculado: " + calculatedSubtotal +
                ", Recibido: " + orderData.getSummary().getSubtotal());
        }

        // Crear la orden
        Order order = new Order();
        order.setOrderNumber(orderData.getOrderNumber());
        order.setTimestamp(LocalDateTime.parse(orderData.getTimestamp(), DateTimeFormatter.ISO_DATE_TIME));
        order.setUser(user);
        order.setFirstName(orderData.getCustomerInfo().getFirstName());
        order.setLastName(orderData.getCustomerInfo().getLastName());
        order.setEmail(orderData.getCustomerInfo().getEmail());
        order.setPhone(orderData.getCustomerInfo().getPhone());
        order.setAddress(orderData.getShippingAddress().getAddress());
        order.setCity(orderData.getShippingAddress().getCity());
        order.setState(orderData.getShippingAddress().getState());
        order.setZipCode(orderData.getShippingAddress().getZipCode());
        order.setSubtotal(orderData.getSummary().getSubtotal());
        order.setShipping(orderData.getSummary().getShipping());
        order.setTotal(orderData.getSummary().getTotal());

        // Agregar items y actualizar stock
        for (int i = 0; i < orderData.getItems().size(); i++) {
            OrderRequestDto.OrderItemDto itemDto = orderData.getItems().get(i);
            Product product = productsToValidate.get(i);

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(itemDto.getId());
            orderItem.setProductName(itemDto.getName());
            orderItem.setPrice(itemDto.getPrice());
            orderItem.setQuantity(itemDto.getQuantity());
            order.addItem(orderItem);

            // Actualizar stock
            ProductStock stock = product.getStock();
            stock.setQuantity(stock.getQuantity() - itemDto.getQuantity());
            productStockRepository.save(stock);
        }

        // Guardar la orden
        Order savedOrder = orderRepository.save(order);

        // Construir respuesta
        return buildOrderResponse(savedOrder);
    }

    private OrderResponseDto buildOrderResponse(Order order) {
        OrderResponseDto response = new OrderResponseDto();
        response.setOrderNumber(order.getOrderNumber());
        response.setTimestamp(order.getTimestamp());
        response.setStatus("COMPLETADA");
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
}
