package com.guohui.o2o.entity;

import java.util.Date;

public class User {

    private Long ownerId;

    private String userName;
    //用户的头像地址
    private String profileImg;

    private String gender;

    private String email;

    private Integer statu;

    private Date creatTime;

    private Date editTime;
    // 1、顾客  2、店家 3、管理员
    private Integer role;

    public Long getUserId() {
        return ownerId;
    }

    public void setUserId(Long userId) {
        this.ownerId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }




}
