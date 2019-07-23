package com.guohui.o2o.service;

import com.guohui.o2o.dto.ShopExecution;
import com.guohui.o2o.entity.Shop;
import com.guohui.o2o.exception.ShopOperationException;

import java.io.InputStream;
import java.util.List;

public interface IShopService {

    ShopExecution addShop(Shop shop, InputStream shopImg ,String fileName) throws ShopOperationException;

    Shop getShopByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, InputStream shopImg ,String fileName) throws ShopOperationException;

    //List<Shop> getShopList(Shop shopCondition,int rowIndex,int pageSize);

}
