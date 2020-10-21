package com.rng.apirng.domain.enums;

public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private Integer codigo;
    private String descricao;

    private EstadoPagamento(Integer codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public static EstadoPagamento toEnum(Integer codigo){
        if(codigo == null) return null;

        for(EstadoPagamento estadoPagamento : EstadoPagamento.values()){
            if(codigo.equals(estadoPagamento.getCodigo())) return estadoPagamento;
        }

        throw new IllegalArgumentException("ID invalido: " + codigo);
    }
}
