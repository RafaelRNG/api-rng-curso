package com.rng.apirng.domain.enums;

public enum TipoCliente {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private Integer codigo;
    private String descricao;

    private TipoCliente(int codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoCliente toEnum(Integer codigo){
        if(codigo == null) return null;

        for(TipoCliente tipocliente : TipoCliente.values()){
            if(codigo.equals(tipocliente.getCodigo())) return tipocliente;
        }

        throw new IllegalArgumentException("ID inválido" + codigo);
    }
}