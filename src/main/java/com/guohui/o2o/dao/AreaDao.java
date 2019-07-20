package com.guohui.o2o.dao;

import com.guohui.o2o.entity.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AreaDao {

    List<Area> queryArea();
}
