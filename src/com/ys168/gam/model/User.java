package com.ys168.gam.model;

import com.ys168.gam.constant.ObjectType;

/**
 * 
 * @author Kevin
 * @since 2017年5月20日
 */
public class User extends Role {

    private transient String httpSessionId;

    private transient String accountId;

    public User() {
        super();
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

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setHttpSessionId(String httpSessionId) {
        this.httpSessionId = httpSessionId;
    }

}
