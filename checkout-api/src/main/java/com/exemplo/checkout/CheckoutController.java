package com.exemplo.checkout;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class CheckoutController {

    private final EstoqueClient estoqueClient;

    public CheckoutController(EstoqueClient estoqueClient) {
        this.estoqueClient = estoqueClient;
    }

    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestBody PedidoRequest pedido) {

        if (pedido.getProduto() == null || pedido.getProduto().isBlank()) {
            return ResponseEntity.badRequest().body("Produto obrigatorio");
        }

        if (pedido.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().body("Quantidade deve ser maior que zero");
        }

        try {
            EstoqueResposta resposta = estoqueClient.verificarEstoque(
                    pedido.getProduto(),
                    pedido.getQuantidade()
            );

            if (resposta != null && resposta.isDisponivel()) {
                System.out.println("Pedido criado: " + pedido.getProduto() + " - Quantidade: " + pedido.getQuantidade());
                return ResponseEntity.ok("Pedido criado com sucesso");
            }

            return ResponseEntity.badRequest().body("Produto sem estoque suficiente");

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao comunicar com o servico de estoque");
        }
    }
}