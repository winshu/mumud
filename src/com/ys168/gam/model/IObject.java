package com.ys168.gam.model;

public interface IObject extends Cloneable {

    String getId();

    String getName();

    String getType();

    IObject clone();
}
