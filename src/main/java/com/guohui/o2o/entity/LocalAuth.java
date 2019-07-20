package com.guohui.o2o.entity;

import java.util.Date;

/**
 * 本地账号
 */
public class LocalAuth {

    private Long LocalAuthId;

    private String username;

    private String password;

    private User user;

    private Date createTime;

    public Long getLocalAuthId() {
        return LocalAuthId;
    }

    public void setLocalAuthId(Long localAuthId) {
        LocalAuthId = localAuthId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    private Date editTime;

}
