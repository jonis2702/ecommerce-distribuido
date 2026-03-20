package com.exemplo.estoque;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private Map<String, Integer> estoque = new HashMap<>();

    public EstoqueController() {
        estoque.put("produto_A", 10);
        estoque.put("produto_B", 5);
    }

    @GetMapping("/verificar")
    public ResponseEntity<?> verificarEstoque(
            @RequestParam String produto,
            @RequestParam int quantidade) {

        if (quantidade <= 0) {
            return ResponseEntity.badRequest().body("Quantidade invalida");
        }

        if (!estoque.containsKey(produto)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto nao foi encontrado");
        }

        int disponivel = estoque.get(produto);

        if (disponivel >= quantidade) {
            return ResponseEntity.ok("Produto esta disponivel");
        } else {
            return ResponseEntity.badRequest()
                    .body("Estoque insuficiente");
        }
    }
}
