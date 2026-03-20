package com.exemplo.checkout;

public class PedidoRequest {

    private String produto;
    private int quantidade;

    public PedidoRequest() {
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}