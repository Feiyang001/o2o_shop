package com.guohui.o2o.dao;

import com.guohui.o2o.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopDao {

    int insertShop(Shop shop);

    int updateShop(Shop shop);

    Shop queryById(long shopId);

    //分页查询店铺 模糊查询 rowIndex 从第几行开始取 pageSize 返回的条数
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex,
                             @Param("pageSize")int pageSize);

    int queryCount(@Param("shopCondition") Shop shopCondition);
}
