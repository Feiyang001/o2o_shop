package com.guohui.o2o.controller;

import com.guohui.o2o.entity.Area;
import com.guohui.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listArea(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        try {
            List<Area> areaList = areaService.getAreaList();
            modelMap.put("areaList",areaList);
            modelMap.put("total",areaList.size());
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
                      modelMap.put("errorMessage",e.toString());
        }
        return modelMap;
    }
}
