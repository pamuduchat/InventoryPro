package com.java.order.service;

import com.java.inventory.dto.InventoryDTO;
import com.java.order.common.ErrorOrderResponse;
import com.java.order.common.OrderResponse;
import com.java.order.common.SuccessOrderRepsonse;
import com.java.order.dto.OrderDTO;
import com.java.order.model.Order;
import com.java.order.repository.OrderRepository;
import com.java.products.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Transactional
public class OrderService {
    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;

    public OrderService(WebClient inventoryWebClient, WebClient productWebClient, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient = productWebClient;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>() {
        }.getType());
    }

    public OrderResponse saveOrder(OrderDTO orderDTO) {
        Integer itemId = orderDTO.getItemId();
        try {
            InventoryDTO inventoryResponse = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/items/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();
            assert inventoryResponse != null;

            Integer productId = inventoryResponse.getProductId();

            ProductDTO productResponse = productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/products/{productId}").build(productId))
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            assert productResponse != null;


            if (inventoryResponse.getQuantity() > 0) {
                if (productResponse.getForSale() == 1) {
                    Order saved = orderRepository.save(modelMapper.map(orderDTO, Order.class));
                    return new SuccessOrderRepsonse(modelMapper.map(saved, OrderDTO.class));
                } else {
                    return new ErrorOrderResponse("This item is not for sale");

                }
            } else {
                return new ErrorOrderResponse("Item not available");
            }
        } catch (WebClientResponseException e) {
            // To be changed
            if (e.getStatusCode().is5xxServerError()) {
                return new ErrorOrderResponse("Item not found");
            }
        }
        return null;
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
