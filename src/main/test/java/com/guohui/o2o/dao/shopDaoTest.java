package com.guohui.o2o.dao;

import com.guohui.o2o.comment.ShopStateEum;
import com.guohui.o2o.entity.Area;
import com.guohui.o2o.entity.Shop;
import com.guohui.o2o.entity.ShopCategory;
import com.guohui.o2o.entity.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class shopDaoTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryByid(){
        long shopId = 30;
        Shop shop = shopDao.queryById(shopId);
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getShopName());
    }


    @Test
    @Ignore
    public void test(){
        Shop shop = new Shop();
        User user = new User();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        user.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(10L);
        shop.setOwner(user);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺33");
        shop.setShopDesc("test3");
        shop.setShopAddr("TEST3");
        shop.setPhone("test3");
        shop.setShopImg("test3");
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");

        int i=shopDao.insertShop(shop);
        assertEquals(1,i);
    }

    @Test
    public void testUpdate(){
        Shop shop = new Shop();
        shop.setShopId(42L);
        shop.setShopName("测试店铺33");
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");

        int i=shopDao.updateShop(shop);
        assertEquals(1,i);
    }




}
