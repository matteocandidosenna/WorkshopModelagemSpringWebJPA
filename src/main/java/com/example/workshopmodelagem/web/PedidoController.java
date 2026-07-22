package com.example.workshopmodelagem.web;
import com.example.workshopmodelagem.domain.Pedido;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.workshopmodelagem.domain.PedidoFactory;
import com.example.workshopmodelagem.domain.ServicoDesconto;
import com.example.workshopmodelagem.repository.PedidoRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoFactory pedidoFactory;
    private final ServicoDesconto servicoDesconto;

    public PedidoController(
            PedidoRepository pedidoRepository,
            PedidoFactory pedidoFactory,
            ServicoDesconto servicoDesconto) {

        this.pedidoRepository = pedidoRepository;
        this.pedidoFactory = pedidoFactory;
        this.servicoDesconto = servicoDesconto;
    }

    @PostMapping
    public Pedido criar(
            @RequestParam String cliente,
            @RequestParam String produto,
            @RequestParam Integer quantidade,
            @RequestParam Double valorUnitario) {

        Double valorBruto = quantidade * valorUnitario;
        Double desconto = servicoDesconto.calcularDesconto(valorBruto);

        Pedido pedido = pedidoFactory.criar(cliente, produto, quantidade, valorUnitario, desconto);

        return pedidoRepository.save(pedido);
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }
}
