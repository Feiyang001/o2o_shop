package com.guohui.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("shopadmin")
public class ShopAdmiController {

    @RequestMapping("/shopoperation")
    public String shopOperation(){
        return "/shop/shopoperation";
    }

}
