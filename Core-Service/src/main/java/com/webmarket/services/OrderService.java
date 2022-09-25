package com.webmarket.services;

import com.webmarket.entities.Order;
import com.webmarket.entities.OrderItem;
import com.webmarket.integrations.CartServiceIntegration;
import com.webmarket.integrations.UserServiceIntegration;
import com.webmarket.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webmarket.cart.CartDto;
import webmarket.core.OrderDetailsDto;
import webmarket.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.webmarket.enums.StatusCode.PAID;


/**
 * The order service creates an order based on data from the shopping cart.
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductService productsService;
    private final UserServiceIntegration userService;

    /**
     * Creates an order based on the received data.
     * @param userName
     * @param orderDetailsDto
     */
    @Transactional
    public void createOrder(String userName, OrderDetailsDto orderDetailsDto) {
        CartDto currentCart = cartServiceIntegration.getUserCart(userName);
        Order order = new Order();
        order.setUsername(userName);
        order.setFio(userService.findByName(userName).getFio());
        order.setPostalCode(orderDetailsDto.getPostalcode());
        order.setAdminAreaTwoTown(orderDetailsDto.getAdminAreaTwoTown());
        order.setAddressLineOneStreet(orderDetailsDto.getAddressLineOneStreet());
        order.setAddressLineTwoApartmentNumber(orderDetailsDto.getAddressLineTwoApartmentNumber());
        order.setEmail(orderDetailsDto.getEmail());
        order.setPhone(orderDetailsDto.getPhone());
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productsService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        ordersRepository.save(order);
        cartServiceIntegration.clearUserCart(userName);
    }

    /**
     * Returns a list of orders for the user.
     * @param username
     * @return
     */
    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }

    /**
     * Returns the Order by its Id.
     * @param id
     * @return
     */
    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }

    /**
     * Saves the order.
     * @param id
     */
    @Transactional
    public void saveOrderById(Long id){
       Order order = findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
       order.setStatus(PAID.name());
    }
}


