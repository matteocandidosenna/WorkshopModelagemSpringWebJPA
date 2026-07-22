package com.example.workshopmodelagem.repository;

import com.example.workshopmodelagem.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}