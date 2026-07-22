package com.example.workshopmodelagem.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    private Double valorBruto;
    private Double desconto;
    private Double valorFinal;
    private String status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    protected Pedido() {
    }

    Pedido(String cliente, Double desconto, ItemPedido item) {
        this.cliente = cliente;
        this.status = "CRIADO";
        this.desconto = desconto;
        adicionarItem(item);
        recalcularTotais();
    }

    private void adicionarItem(ItemPedido item) {
        item.vincularAoPedido(this);
        itens.add(item);
    }

    private void recalcularTotais() {
        this.valorBruto = itens.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
        this.valorFinal = valorBruto - desconto;
    }

    public Long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public Double getValorBruto() {
        return valorBruto;
    }

    public Double getDesconto() {
        return desconto;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public Double getValor() {
        return valorFinal;
    }

    public String getStatus() {
        return status;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }
}
