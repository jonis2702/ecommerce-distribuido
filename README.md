# 🛒 E-commerce Distribuído - Marco 1

## 📌 Objetivo
Implementar a comunicação síncrona entre o **Checkout API** e o **Serviço de Estoque**, utilizando microsserviços em Java com Spring Boot.

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- Maven
- REST API
- Git e GitHub

---

## 🏗️ Arquitetura

O sistema é composto por dois microsserviços:

### 🔹 Estoque Service
Responsável por gerenciar e verificar a disponibilidade de produtos.

- Porta: **8081**
- Endpoint:

---

### 🔹 Checkout API
Responsável por receber pedidos e consultar o estoque antes de confirmar a compra.

- Porta: **8082**
- Endpoint:

---

## 🔄 Fluxo de Funcionamento

1. O cliente envia um pedido para o **Checkout API**.
2. O Checkout faz uma requisição HTTP síncrona ao **Serviço de Estoque**.
3. O Estoque verifica a disponibilidade do produto.
4. O Checkout decide:
   - ✔️ Se disponível → cria o pedido
   - ❌ Se indisponível → retorna erro

---

## 📦 Exemplo de Requisição

### POST - Criar Pedido

### Body (JSON)

```json
{
  "produto": "produto_A",
  "quantidade": 2
}