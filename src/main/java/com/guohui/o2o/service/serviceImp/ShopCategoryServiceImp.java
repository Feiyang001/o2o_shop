package com.guohui.o2o.service.serviceImp;

import com.guohui.o2o.dao.ShopCategoryDao;
import com.guohui.o2o.entity.ShopCategory;
import com.guohui.o2o.service.IShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImp implements IShopCategoryService {


    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryCategory(shopCategoryCondition);
    }
}
