package com.rng.apirng.domain.enums;

public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private Integer codigo;
    private String descricao;

    private Perfil(Integer codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public static Perfil toEnum(Integer codigo){
        if(codigo == null) return null;

        for(Perfil estadoPagamento : Perfil.values()){
            if(codigo.equals(estadoPagamento.getCodigo())) return estadoPagamento;
        }

        throw new IllegalArgumentException("ID invalido: " + codigo);
    }
}
