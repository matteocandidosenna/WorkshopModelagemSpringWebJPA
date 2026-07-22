package com.example.workshopmodelagem.domain;

import org.springframework.stereotype.Component;

@Component
public class PedidoFactory {

    public Pedido criar(String cliente, String produto, Integer quantidade, Double valorUnitario, Double desconto) {
        ItemPedido item = new ItemPedido(produto, quantidade, valorUnitario);
        return new Pedido(cliente, desconto, item);
    }
}
