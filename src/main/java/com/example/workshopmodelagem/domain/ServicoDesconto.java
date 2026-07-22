package com.example.workshopmodelagem.domain;

import org.springframework.stereotype.Service;

@Service
public class ServicoDesconto {

    public Double calcularDesconto(Double valorBruto) {
        if (valorBruto > 100) {
            return valorBruto * 0.10;
        }

        return 0.0;
    }

    public Double calcularValorFinal(Double valorBruto) {
        return valorBruto - calcularDesconto(valorBruto);
    }
}
