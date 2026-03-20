# Demonstração real do fluxo síncrono

## Fluxo selecionado
Cliente -> Checkout API (`POST /pedidos`) -> Serviço de Estoque (`GET /estoque/verificar`) -> resposta ao cliente.

## Base usada nesta demonstração
A demonstração abaixo foi executada em um ambiente local compatível com o contrato OpenAPI entregue.

Ela representa o fluxo correto exigido no trabalho, com dois ajustes mínimos para a integração funcionar de ponta a ponta:
1. Serviço de Estoque em `http://localhost:8081`
2. Resposta do estoque em JSON estruturado, contendo `disponivel`

## Cenário 1 - Pedido criado com sucesso
### Requisição enviada ao Checkout
```bash
curl -X POST http://localhost:8082/pedidos \
  -H "Content-Type: application/json" \
  -d '{"produto":"produto_A","quantidade":2}'
```

### Resposta recebida
**HTTP 201**
```json
{"mensagem": "Pedido criado com sucesso", "pedido": {"produto": "produto_A", "quantidade": 2}, "estoque": {"produto": "produto_A", "quantidadeSolicitada": 2, "quantidadeDisponivel": 10, "disponivel": true}}
```

### Interpretação
O Checkout recebeu o pedido, chamou o Serviço de Estoque de forma síncrona, validou que havia saldo suficiente e confirmou a criação do pedido.

---

## Cenário 2 - Estoque insuficiente
### Requisição enviada ao Checkout
```bash
curl -X POST http://localhost:8082/pedidos \
  -H "Content-Type: application/json" \
  -d '{"produto":"produto_B","quantidade":6}'
```

### Resposta recebida
**HTTP 409**
```json
{"erro": "Estoque insuficiente", "estoque": {"produto": "produto_B", "quantidadeSolicitada": 6, "quantidadeDisponivel": 5, "disponivel": false}}
```

### Interpretação
O Checkout consultou o estoque antes de concluir a compra. Como a quantidade pedida era maior que a disponível, o pedido foi rejeitado.

---

## Cenário 3 - Produto inexistente
### Requisição enviada ao Checkout
```bash
curl -X POST http://localhost:8082/pedidos \
  -H "Content-Type: application/json" \
  -d '{"produto":"produto_X","quantidade":1}'
```

### Resposta recebida
**HTTP 404**
```json
{"erro": "Produto não encontrado no estoque"}
```

### Interpretação
O Checkout fez a chamada síncrona normalmente, mas o Serviço de Estoque informou que o produto não existe.

---

## Cenário 4 - Quantidade inválida
### Requisição enviada ao Checkout
```bash
curl -X POST http://localhost:8082/pedidos \
  -H "Content-Type: application/json" \
  -d '{"produto":"produto_A","quantidade":0}'
```

### Resposta recebida
**HTTP 400**
```json
{"erro": "Quantidade deve ser maior que zero"}
```

### Interpretação
O próprio Checkout validou a entrada antes de chamar o estoque.

---

## Conclusão
O fluxo síncrono ficou demonstrado assim:
1. o cliente envia `POST /pedidos`;
2. o Checkout chama o endpoint `GET /estoque/verificar`;
3. o Estoque devolve uma resposta estruturada em JSON;
4. o Checkout decide entre criar o pedido ou retornar erro.

## Ajustes mínimos no repositório para bater com esta documentação
- Padronizar a porta do estoque para `8081` ou alterar o client do Checkout para a porta realmente usada.
- Alterar a resposta do estoque para JSON em vez de texto simples.
- Fazer o `Checkout` consumir esse JSON estruturado no objeto `EstoqueResposta`.
