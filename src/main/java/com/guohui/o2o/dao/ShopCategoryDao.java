package com.guohui.o2o.dao;

import com.guohui.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {

    List<ShopCategory> queryCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);
}
