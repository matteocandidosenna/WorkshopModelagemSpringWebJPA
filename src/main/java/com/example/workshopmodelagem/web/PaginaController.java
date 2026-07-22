package com.example.workshopmodelagem.web;

import com.example.workshopmodelagem.domain.Pedido;
import com.example.workshopmodelagem.domain.PedidoFactory;
import com.example.workshopmodelagem.domain.ServicoDesconto;
import com.example.workshopmodelagem.repository.PedidoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class PaginaController {

    private final PedidoRepository pedidoRepository;
    private final PedidoFactory pedidoFactory;
    private final ServicoDesconto servicoDesconto;

    public PaginaController(
            PedidoRepository pedidoRepository,
            PedidoFactory pedidoFactory,
            ServicoDesconto servicoDesconto) {

        this.pedidoRepository = pedidoRepository;
        this.pedidoFactory = pedidoFactory;
        this.servicoDesconto = servicoDesconto;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/pedidos";
    }

    @GetMapping("/pedidos")
    public String pedidos(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        model.addAttribute("conceitosDdd", List.of(
                "Entidade: Pedido possui identidade propria e ciclo de vida.",
                "Entidade relacionada: ItemPedido pertence ao agregado Pedido.",
                "Factory: centraliza a criacao valida do agregado.",
                "Servico de Dominio: calcula desconto por uma regra do negocio.",
                "Repositorio: abstrai a persistencia e recuperacao dos pedidos."
        ));
        return "index";
    }

    @PostMapping("/pedidos/novo")
    public String criarPedido(
            @RequestParam String cliente,
            @RequestParam String produto,
            @RequestParam Integer quantidade,
            @RequestParam Double valorUnitario,
            RedirectAttributes redirectAttributes) {

        Double valorBruto = quantidade * valorUnitario;
        Double desconto = servicoDesconto.calcularDesconto(valorBruto);
        Pedido pedido = pedidoFactory.criar(cliente, produto, quantidade, valorUnitario, desconto);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        redirectAttributes.addFlashAttribute("pedidoCriado", pedidoSalvo);
        redirectAttributes.addFlashAttribute("fluxoDdd", List.of(
                "1. Entrada: a camada web recebeu cliente, produto, quantidade e valor unitario.",
                "2. Servico de Dominio: ServicoDesconto calculou a regra de desconto sobre o valor bruto.",
                "3. Factory: PedidoFactory montou o agregado Pedido com seu ItemPedido.",
                "4. Entidade: Pedido recalculou seus totais internos e manteve seu status como CRIADO.",
                "5. Repositorio: PedidoRepository salvou o agregado e gerou o identificador do pedido."
        ));

        return "redirect:/pedidos";
    }
}
