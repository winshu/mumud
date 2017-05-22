package com.ys168.gam.simple;

import com.alibaba.fastjson.annotation.JSONField;
import com.ys168.gam.constant.ObjectType;
import com.ys168.gam.model.IObject;

/**
 * 
 * @author Kevin
 * @since 2017年5月21日
 */
public class SimpleObjectInfo {

    public static SimpleObjectInfo create(IObject object) {
        SimpleObjectInfo simple = new SimpleObjectInfo();
        simple.id = object.getId();
        simple.name = object.getName();
        simple.type = object.getType();
        return simple;
    }

    private String id;
    private String name;
    private ObjectType type;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JSONField(serialize = false)
    public ObjectType getObjectType() {
        return type;
    }

    public String getType() {
        return type.toType();
    }

}
