package com.java.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    public WebClient inventoryWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder.baseUrl("http://apigateway/api/v1").build();
    }

    @Bean
    public WebClient productWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder.baseUrl("http://apigateway/api/v1").build();
    }
}
