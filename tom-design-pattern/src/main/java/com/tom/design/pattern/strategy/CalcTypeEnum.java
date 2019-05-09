package com.tom.design.pattern.strategy;

public enum CalcTypeEnum {

    ADD(1),
    SUB(2),
    MUL(3);

    private Integer type;

    CalcTypeEnum(int type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
