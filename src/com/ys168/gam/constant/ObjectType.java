package com.ys168.gam.constant;

public enum ObjectType {

    ITEM, NPC, USER;

    public boolean isItem() {
        return this.equals(ITEM);
    }
    
    public boolean isNpc() {
        return this.equals(NPC);
    }
    
    public boolean isUser() {
        return this.equals(USER);
    }

}
