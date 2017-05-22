package com.ys168.gam.model;

import java.util.HashMap;
import java.util.Map;

import com.ys168.gam.constant.ObjectType;

/**
 * 
 * @author Kevin
 * @since 2017年5月22日
 */
public class Item implements IObject {

    private transient Map<String, Object> attributes;
    private transient boolean isTemp;

    public Item(String id, String name) {
        this.attributes = new HashMap<>();
        setId(id);
        setName(name);
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

    protected Object getAttribute(String attribute) {
        return attributes.get(attribute);
    }

    public String getDesc() {
        return (String) getAttribute("desc");
    }

    public String getId() {
        return (String) getAttribute("id");
    }

    public String getName() {
        return (String) getAttribute("name");
    }

    @Override
    public ObjectType getType() {
        return ObjectType.ITEM;
    }

    public boolean isTemp() {
        return isTemp;
    }

    public void setAttribute(String attribute, Object value) {
        attributes.put(attribute, value);
    }

    public void setDesc(String desc) {
        setAttribute("desc", desc);
    }

    public void setId(String id) {
        setAttribute("id", id);
    }

    public void setName(String name) {
        setAttribute("name", name);
    }

    public void setTemp(boolean isTemp) {
        this.isTemp = isTemp;
    }
    
    public Map<String, Object> toOutputInfo() {
        Map<String, Object> output = new HashMap<>();
        output.put("id", getId());
        output.put("name", getName());
        output.put("type", getType().toType());
        output.put("desc", getDesc());

        return output;
    }

}
