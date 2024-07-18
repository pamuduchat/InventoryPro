package com.java.order.service;

import com.java.order.dto.OrderDTO;
import com.java.order.model.Order;
import com.java.order.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDTO> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>() {
        }.getType());
    }

    public OrderDTO saveOrder(OrderDTO orderDTO) {
        Order saved = orderRepository.save(modelMapper.map(orderDTO, Order.class));
        return modelMapper.map(saved, OrderDTO.class);
    }

    public OrderDTO updateOrder(OrderDTO orderDTO) {
        Order saved = orderRepository.save(modelMapper.map(orderDTO, Order.class));
        return modelMapper.map(saved, OrderDTO.class);
    }

    public String deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
        return "Order deleted successfully";
    }

    public OrderDTO getOrderById(Integer orderId) {
        Order order = orderRepository.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }
}
