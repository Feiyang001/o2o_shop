package com.guohui.o2o.comment;

public enum ShopStateEum {

    CHECK(0,"S审核中"),
    SUCCESS(1,"操作成功"),
    OFFICE(-1,"非法的店铺"),
    PASS(2,"通过认证"),
    INNER_ERROR(-1001,"内部系统错误"),
    NULL_SHOP(-1003,"NULL_SHOP");

    private int state;

    private String stateInfo;

    private ShopStateEum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 根据传入的state值返回相应的enum值
     * @return
     */
    public static ShopStateEum stateOf(int state){
        for (ShopStateEum stateEum :values()){
            if (stateEum.getState() == state){
                return stateEum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }



}
