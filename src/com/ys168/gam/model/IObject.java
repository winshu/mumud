package com.ys168.gam.model;

import com.ys168.gam.constant.ObjectType;

public interface IObject extends Cloneable {

    String getId();

    String getName();

    ObjectType getType();

    IObject clone();
}
