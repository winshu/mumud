package com.ys168.gam.model;

import java.util.LinkedHashSet;
import java.util.Set;

import com.ys168.gam.constant.ObjectType;
import com.ys168.gam.simple.UserInfo;

/**
 * 
 * @author Kevin
 * @since 2017年5月20日
 */
public class User extends Role {

    private transient String httpSessionId;

    private transient String accountId;

    private transient String password;

    private transient Set<Item> warehouse;// 仓库

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

    @Override
    public ObjectType getType() {
        return ObjectType.USER;
    }

    public UserInfo toSimpleInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(getId());
        userInfo.setName(getName());
        userInfo.setDesc(getDescription());

        return userInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
