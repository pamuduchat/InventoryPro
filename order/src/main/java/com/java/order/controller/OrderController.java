package com.java.order.controller;

import com.abccompany.base.dto.OrderEventDTO;
import com.java.order.common.OrderResponse;
import com.java.order.dto.OrderDTO;
import com.java.order.kafka.OrderProducer;
import com.java.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProducer orderProducer;

    @GetMapping("/orders")
    public List<OrderDTO> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public OrderDTO getOrdersById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping("/orders")
    public OrderResponse saveOrder(@RequestBody OrderDTO orderDTO) {
        OrderEventDTO orderEventDTO = new OrderEventDTO();
        orderEventDTO.setMessage("Order is commited");
        orderEventDTO.setStatus("pending");
        orderProducer.sendMessage(orderEventDTO);
        return orderService.saveOrder(orderDTO);
    }

    @PutMapping("/orders")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(orderDTO);
    }

    @DeleteMapping("/orders/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId) {
        return orderService.deleteOrder(orderId);
    }
}
