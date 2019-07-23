package com.guohui.o2o.service;


import com.guohui.o2o.comment.ShopStateEum;
import com.guohui.o2o.dto.ShopExecution;
import com.guohui.o2o.entity.Area;
import com.guohui.o2o.entity.Shop;
import com.guohui.o2o.entity.ShopCategory;
import com.guohui.o2o.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * 测试Dao
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring 的配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class testService {

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IShopService iShopService;

    @Test
    public void testS(){
        List<Area>  areaList = areaService.getAreaList();
        System.out.println(areaList.size());
    }

    @Test
    public void test2() throws FileNotFoundException {
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
        shop.setShopName("测试店铺2");
        shop.setShopDesc("test2");
        shop.setShopAddr("TEST2");
        shop.setPhone("test2");
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(ShopStateEum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("C:/Users/Administrator/Desktop/Resources/gouI.jpg");

        InputStream in = new FileInputStream(shopImg);
        ShopExecution shopExecution = iShopService.addShop(shop, in,shopImg.getName());

        assertEquals(ShopStateEum.CHECK.getState(),shopExecution.getState());
    }

}
