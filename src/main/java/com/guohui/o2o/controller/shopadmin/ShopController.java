package com.guohui.o2o.controller.shopadmin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.guohui.o2o.comment.ShopStateEum;
import com.guohui.o2o.dto.ShopExecution;
import com.guohui.o2o.entity.Area;
import com.guohui.o2o.entity.Shop;
import com.guohui.o2o.entity.ShopCategory;
import com.guohui.o2o.entity.User;
import com.guohui.o2o.service.IAreaService;
import com.guohui.o2o.service.IShopCategoryService;
import com.guohui.o2o.service.IShopService;
import com.guohui.o2o.utils.CodeUtil;
import com.guohui.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shopadmin")
public class ShopController {

    @Autowired
    private IShopService iShopService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IShopCategoryService iShopCategoryService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errorMsg", "验证码错误");
            return modelMap;
        }
        //接受前端传来的信息并转化相应的参数 包括店铺的信息及图片
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //将 string 转化为 shop 模式 详细转换方式可参见 Github 中的FasterXML/jackson的jackson的使用
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errorMsg", e.toString());
            return modelMap;
        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commosMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commosMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg"); //从前端的一个shopImg的变量里面传过来的
        } else {
            modelMap.put("success", false);
            modelMap.put("errorMsg", "上传图片不能为空");
            return modelMap;
        }
        //注册店铺
        if (shop != null && shopImg != null) {
            User owner = (User)request.getSession().getAttribute("user");
            // TODO 从session中获取ownerId
            shop.setOwner(owner);
            ShopExecution shopExecution;
            try {
                shopExecution = iShopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                if (shopExecution.getState() == ShopStateEum.CHECK.getState()) {
                    modelMap.put("success", true);
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0){
                        shopList = new ArrayList<Shop>();
                    }
                    shopList.add(shopExecution.getShop());
                    request.getSession().setAttribute("shopList",shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errorMsg", shopExecution.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errorMsg", e.toString());
                return modelMap;
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errorMsg", "店铺的信息是空de");
            return modelMap;
        }

    }


    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //先校验验证码是否正确
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errorMsg", "验证码错误");
            return modelMap;
        }
        //接受前端传来的信息并转化相应的参数 包括店铺的信息及图片
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //将 string 转化为 shop 模式 详细转换方式可参见 Github 中的FasterXML/jackson的jackson的使用
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errorMsg", e.toString());
            return modelMap;
        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commosMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commosMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg"); //从前端的一个shopImg的变量里面传过来的
        }
        //修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution shopExecution;
            try {
                if (shopImg == null) {
                    shopExecution = iShopService.modifyShop(shop, null, null);
                } else {
                    shopExecution = iShopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                }
                if (shopExecution.getState() == ShopStateEum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errorMsg", shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errorMsg", e.toString());
                return modelMap;
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errorMsg", "输入店铺的Id");
            return modelMap;
        }

    }


    /**
     * 把 CommonsMultipartFile 转化为 File 的类型 借助CommonsMultipartFile的方法
     * 借助inputStreamFile为中介进行转换
     *
     * @param
     * @param //private static void inputStreamFileToFile(InputStream in,File file){
     *                  OutputStream os = null;
     *                  try {
     *                  os = new FileOutputStream(file);
     *                  int bytesRead = 0;
     *                  byte [] buffer = new byte[1024];
     *                  while ((bytesRead = in.read(buffer))!= -1){
     *                  os.write(buffer,0,bytesRead);
     *                  }
     *                  } catch (Exception e) {
     *                  throw new RuntimeException("file文件转化异常："+e.getMessage());
     *                  } finally {
     *                  try {
     *                  if (os != null) {
     *                  os.close();
     *                  }
     *                  if (in != null) {
     *                  in.close();
     *                  }
     *                  } catch (IOException e) {
     *                  e.printStackTrace();
     *                  }
     *                  }
     *                  }
     */
    @RequestMapping(value = "/getshopbyshopid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");//  shopId 前端传过来的
        if (shopId > -1) {
            try {
                Shop shop = iShopService.getShopByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errorMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errorMsg", "emptySHopID");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {

        System.out.println("***********************开始*************************");

        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Area> areaList = new ArrayList<Area>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();

        try {
            areaList = areaService.getAreaList();
            shopCategoryList = iShopCategoryService.getShopCategoryList(new ShopCategory());

            System.out.println("********************areaList****************************" + areaList);

            System.out.println("********************shopCategoryList****************************" + shopCategoryList);

            modelMap.put("areaList", areaList);
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("success", true);

        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errorMsg", e.getMessage());
        }
        return modelMap;
    }


}
