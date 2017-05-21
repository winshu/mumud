package com.ys168.gam.model;

import com.ys168.gam.constant.ObjectType;

public class Npc extends Role {

    @Override
    public ObjectType getType() {
        return ObjectType.NPC;
    }

    @Override
    protected String buildDesc() {
        if (getAttribute("desc") != null) {
            return (String) getAttribute("desc");
        }
        return "这是一个NPC";
    }
}
