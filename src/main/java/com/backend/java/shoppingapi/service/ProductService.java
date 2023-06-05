package com.backend.java.shoppingapi.service;

import com.santana.java.back.end.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private String productApiURL = "http://localhost:8081";

    public ProductDTO getProductByIdentifier(String productIdentifier) {
        try {
            WebClient webClient = WebClient.builder()
                    .baseUrl(productApiURL)
                    .build();
            Mono<ProductDTO> product = webClient.get()
                    .uri("/product/" + productIdentifier)
                    .retrieve()
                    .bodyToMono(ProductDTO.class);
            return product.block();
        } catch (Exception e) {
            throw new RuntimeException("Product	not	found");
        }
    }

}
