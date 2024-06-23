package com.labdessoft.roteiro01.entity;

public enum TaskTypeEnum {

    DATA("Data"),
    PRAZO("Prazo"),
    LIVRE("Livre");

    private final String descricao;

    TaskTypeEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
