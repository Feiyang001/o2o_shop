package com.guohui.o2o.service.serviceImp;

import com.guohui.o2o.dao.AreaDao;
import com.guohui.o2o.entity.Area;
import com.guohui.o2o.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("areaService")
public class AreaServiceImp implements IAreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
