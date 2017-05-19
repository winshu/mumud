package com.ys168.gam.model;

import com.ys168.gam.constant.ObjectType;

public class Npc extends Role {

    @Override
    public ObjectType getType() {
        return ObjectType.NPC;
    }

    @Override
    public Object toSimpleInfo() {
        return clone();
    }
}
