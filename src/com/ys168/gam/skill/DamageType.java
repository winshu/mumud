package com.ys168.gam.skill;

public enum DamageType {

    PUNCTURE("PUNCTURE", "刺伤");

    DamageType(String code, String name) {
        this._CODE = code;
        this._NAME = name;
    }

    public String _CODE;
    public String _NAME;
}
