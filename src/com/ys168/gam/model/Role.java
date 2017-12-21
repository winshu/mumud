package com.ys168.gam.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Kevin
 * @since 2017年5月21日
 */
public abstract class Role implements IObject {

    protected final Bag bag;

    private final Map<String, Object> attributes;
    private boolean isFighting;
    private boolean isBusy;
    private Room room; // Room不序列化，否则会出现循环序列

    public Role() {
        this.attributes = new HashMap<>();
        this.bag = new Bag();
    }

    protected abstract String buildDesc();

    public void changeRoom(Room room) {
        if (this.room != null) {
            this.room.removeObject(this);
        }
        this.room = room;
        if (this.room != null) {
            this.room.addObject(this);
        }
    }

    @Override
    public Role clone() {
        try {
            return (Role) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public Object getAttribute(String attribute) {
        return attributes.get(attribute);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Bag getBag() {
        return bag;
    }

    public String getId() {
        return (String) getAttribute("id");
    }

    public String getName() {
        return (String) getAttribute("name");
    }

    public Room getRoom() {
        return room;
    }

    protected boolean hasAttribute(String attribute) {
        return attributes.containsKey(attribute);
    }

    public boolean isBusy() {
        return isBusy;
    }

    public boolean isFighting() {
        return isFighting;
    }

    public void setAttribute(String attribute, Object value) {
        attributes.put(attribute, value);
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public void setFighting(boolean isFighting) {
        this.isFighting = isFighting;
    }

    public void setId(String id) {
        setAttribute("id", id);
    }

    public void setName(String name) {
        setAttribute("name", name);
    }

    public Map<String, Object> toOutputInfo() {
        Map<String, Object> output = new HashMap<>();
        output.put("id", getId());
        output.put("name", getName());
        output.put("type", getType().toType());
        output.put("desc", buildDesc());

        return output;
    }

}
