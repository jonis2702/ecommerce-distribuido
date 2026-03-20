package com.exemplo.checkout;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EstoqueClient {

    private final RestTemplate restTemplate;

    public EstoqueClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EstoqueResposta verificarEstoque(String produto, int quantidade) {
        String url = "http://localhost:8081/estoque/verificar?produto=" + produto + "&quantidade=" + quantidade;

        ResponseEntity<EstoqueResposta> response =
                restTemplate.getForEntity(url, EstoqueResposta.class);

        return response.getBody();
    }
}