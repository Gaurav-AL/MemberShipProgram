package com.membership.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.membership.Repository.OrderRepository;
import com.membership.Repository.ProductRepository;
import com.membership.dao.Operations;
import com.membership.dao.Order;
import com.membership.dao.Product;
import com.membership.dto.OrderRequest;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderTransactionService orderTransactionService;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order createOrders(OrderRequest request){
         // Check idempotency FIRST

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setProduct(product);
        order.setOrderAmount(product.getProductAmount());
        order.setOrderDateTime(LocalDateTime.now());
        
        Order savedOrder = orderRepository.save(order);

        // 2️⃣ Create transaction once
        orderTransactionService.createBuyTransaction(savedOrder, request.getIdempotencyKey());

        return savedOrder;
    }

    public List<Order> getOrderByUserId(Long userId) {
        
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders;
    }
        
}
