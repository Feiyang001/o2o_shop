package com.guohui.o2o.dto;


import com.guohui.o2o.comment.ShopStateEum;
import com.guohui.o2o.entity.Shop;

import java.util.List;

/**
 * 存储的是店铺的信息 和店铺的状态
 * 针对的是 shop 的返回信息的封装
 */
public class ShopExecution {

    //结果的状态
    private int state;

    //状态信息
    private String stateInfo;

    //店铺的数量
    private int count;

    //操作的shop(增删改)
    private Shop shop;

    //shop列表（查询店铺列表的时候用）
    private List<Shop> shops;

    public ShopExecution() {
    }

    //店铺操作失败时候的构造器
    public ShopExecution(ShopStateEum stateEum){
        this.state = stateEum.getState();
        this.stateInfo = stateEum.getStateInfo();
    }

    //成功的构造器
    public ShopExecution(ShopStateEum stateEum,Shop shop){
        this.state = stateEum.getState();
        this.stateInfo = stateEum.getStateInfo();
        this.shop = shop;
    }

    //返回一个列表
    public ShopExecution(ShopStateEum stateEum,List<Shop> shops){
        this.state = stateEum.getState();
        this.stateInfo = stateEum.getStateInfo();
        this.shops = shops;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}
