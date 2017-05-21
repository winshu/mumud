package com.ys168.gam.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.ys168.gam.constant.ObjectType;

/**
 * 
 * @author Kevin
 * @since 2017年5月20日
 */
public class User extends Role {

    private transient String httpSessionId;

    private transient String accountId;

    private transient Set<Item> warehouse;// 仓库

    public User() {
        super();
        this.warehouse = new LinkedHashSet<>();
    }

    @Override
    protected String buildDesc() {
        return "这是一个用户";
    }

    public String getAccountId() {
        return accountId;
    }

    public String getHttpSessionId() {
        return httpSessionId;
    }

    @Override
    public ObjectType getType() {
        return ObjectType.USER;
    }

    public Set<Item> getWarehouse() {
        return warehouse;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setHttpSessionId(String httpSessionId) {
        this.httpSessionId = httpSessionId;
    }

}
