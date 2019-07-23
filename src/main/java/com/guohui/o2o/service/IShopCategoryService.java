package com.guohui.o2o.service;

import com.guohui.o2o.entity.ShopCategory;

import java.util.List;

public interface IShopCategoryService {


    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
