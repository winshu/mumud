package com.ys168.gam.constant;

/**
 * 
 * @author Kevin
 * @since 2017年5月21日
 */
public enum ObjectType {

    NPC, ITEM, USER;

    public boolean isItem() {
        return this.equals(ITEM);
    }
    
    public boolean isNpc() {
        return this.equals(NPC);
    }
    
    public boolean isUser() {
        return this.equals(USER);
    }

    public String toType() {
        return name().toLowerCase();
    }
}
