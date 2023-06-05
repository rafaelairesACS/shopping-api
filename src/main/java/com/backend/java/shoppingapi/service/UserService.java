package com.backend.java.shoppingapi.service;


import com.santana.java.back.end.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private String userApiURL = "http://localhost:8080";

    public UserDTO getUserByCpf(String cpf) {
        try {
            WebClient webClient = WebClient.builder()
                    .baseUrl(userApiURL)
                    .build();
            Mono<UserDTO> user = webClient.get()
                    .uri("/user/" + cpf + "/cpf")
                    .retrieve()
                    .bodyToMono(UserDTO.class);
            return user.block();
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }
}
