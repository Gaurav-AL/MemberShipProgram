package com.membership.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.membership.Repository.OrderTransactionRepository;
import com.membership.dao.Order;
import com.membership.dao.OrderTransaction;
import com.membership.dao.OrderTransactionType;

import jakarta.transaction.Transactional;

@Service
public class OrderTransactionService {
    
    @Autowired
    private OrderTransactionRepository orderTransactionRepository;

    public List<OrderTransaction> getAllOrderTransactionByUserId(Long userId){
        return orderTransactionRepository.findByUserId(userId);
    }

    @Transactional
    public void createBuyTransaction(Order order) {

        Optional<OrderTransaction> existing =
            orderTransactionRepository.findByIdempotencyKey(order.getIdempotencyKey());

        if (existing.isPresent()) {
            return; // already Processed
        }

        OrderTransaction tx = new OrderTransaction();
        tx.setUserId(order.getUserId());
        tx.setOrderId(order.getId());
        tx.setAmount(order.getOrderAmount());
        tx.setOrderTransactionType(OrderTransactionType.BUY);
        tx.setIdempotencyKey(order.getIdempotencyKey());
        tx.setCreatedAt(LocalDateTime.now());

        orderTransactionRepository.save(tx);

    }

}
