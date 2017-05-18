package com.ys168.gam.model;

public class Item implements IObject {

    private String id;
    private int itemType;
    private String name;
    private String description;
    private boolean isTemp;

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public int getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return getClass().getSimpleName().toLowerCase();
    }

    public boolean isTemp() {
        return isTemp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTemp(boolean isTemp) {
        this.isTemp = isTemp;
    }

    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
