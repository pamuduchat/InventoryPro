package com.java.order.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.java.order.dto.OrderDTO;
import lombok.Getter;

@Getter
public class SuccessOrderRepsonse implements OrderResponse{
    @JsonUnwrapped
    private final OrderDTO order;

    public SuccessOrderRepsonse(OrderDTO order){
        this.order = order;
    }
}
