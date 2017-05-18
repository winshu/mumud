package com.ys168.gam.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;

public class User extends Role {

    @JSONField(serialize = false)
    private String httpSessionId;

    @JSONField(serialize = false)
    private String accountId;

    @JSONField(serialize = false)
    private Set<Item> warehouse;// 仓库

    public User() {
        super();
        this.warehouse = new LinkedHashSet<>();
    }

    public Set<Item> getWarehouse() {
        return warehouse;
    }

    public String getHttpSessionId() {
        return httpSessionId;
    }

    public void setHttpSessionId(String httpSessionId) {
        this.httpSessionId = httpSessionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}