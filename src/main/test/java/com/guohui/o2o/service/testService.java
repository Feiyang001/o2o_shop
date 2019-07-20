package com.guohui.o2o.service;


import com.guohui.o2o.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    private AreaService areaService;

    @Test
    public void testS(){
        List<Area>  areaList = areaService.getAreaList();
        System.out.println(areaList.size());
    }

}
