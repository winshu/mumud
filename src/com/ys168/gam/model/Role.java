package com.ys168.gam.model;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Role implements IObject {

    private String id;
    private String name;
    private String description;
    private boolean isFighting;
    private boolean isBusy;

    private Room room;
    protected final Set<Item> bag;

    public Role() {
        this.bag = new LinkedHashSet<>();
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return getClass().getSimpleName().toLowerCase();
    }

    public boolean isBusy() {
        return isBusy;
    }

    public boolean isFighting() {
        return isFighting;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFighting(boolean isFighting) {
        this.isFighting = isFighting;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        if (this.room != null) {
            this.room.removeObject(this);
        }
        this.room = room;
        this.room.addObject(this);
    }

    @Override
    public Role clone() {
        try {
            return (Role) super.clone();
        }
        catch (Exception e) {
            return null;
        }
    }
}
