package com.rng.apirng.domain;

import com.rng.apirng.domain.enums.EstadoPagamento;

import javax.persistence.Entity;

@Entity
public class PagamentoComCartao extends Pagamento {

    private static final long serialVersionUID = 1L;

    private Long numeroDeParcelas;

    public PagamentoComCartao(){}

    public PagamentoComCartao(Long id, EstadoPagamento estadoPagamento, Pedido pedido, Long numeroDeParcelas) {
        super(id, estadoPagamento, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Long getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Long numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }
}