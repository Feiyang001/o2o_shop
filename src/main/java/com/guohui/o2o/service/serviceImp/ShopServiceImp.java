package com.guohui.o2o.service.serviceImp;

import com.guohui.o2o.comment.ShopStateEum;
import com.guohui.o2o.dao.ShopDao;
import com.guohui.o2o.dto.ShopExecution;
import com.guohui.o2o.entity.Shop;
import com.guohui.o2o.exception.ShopOperationException;
import com.guohui.o2o.service.IShopService;
import com.guohui.o2o.utils.ImageUtil;
import com.guohui.o2o.utils.Pathutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;

@Service("iShopService")
public class ShopServiceImp implements IShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
        //空值判断
        if (shop == null) {
            return new ShopExecution(ShopStateEum.NULL_SHOP);
        }
        //如果有必要还得对shop 里面的一些关联其他表的外键进行非空的判断
        try {
            //初始值设置
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            int resultCount = shopDao.insertShop(shop);
            if (resultCount <= 0) {
                throw new ShopOperationException("addShop failed");
            } else {
                //判断传入的文件是否为空
                if (shopImgInputStream != null) {
                    //存储图片
                    try {
                        addImage(shop, shopImgInputStream, fileName);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error :" + e.getMessage());
                    }
                    //更新shop的图片的地址
                    resultCount = shopDao.updateShop(shop);
                    if (resultCount <= 0) {
                        throw new ShopOperationException("更新图片的地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error ：" + e.getMessage());
        }
        return new ShopExecution(ShopStateEum.CHECK, shop);
    }

    @Override
    public Shop getShopByShopId(long shopId) {
        return shopDao.queryById(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImg, String fileName) throws ShopOperationException {
        //判断是否需要出处理图片  有新的图片的时候 需要删除旧的图片
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEum.NULL_SHOP);
        } else {
            try {
                if (shopImg != null && fileName != null && "".equals(fileName)) {
                    Shop tempShop = shopDao.queryById(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addImage(shop, shopImg, fileName);
                }
                //更新店铺信息
                shop.setLastEditTime(new Date());
                int resulCount = shopDao.updateShop(shop);
                if (resulCount <= 0) {
                    return new ShopExecution(ShopStateEum.INNER_ERROR);
                } else {
                    shop = shopDao.queryById(shop.getShopId());
                    return new ShopExecution(ShopStateEum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("MODIFY SHOP ERROR:" + e.getMessage());
            }
        }
    }

    private void addImage(Shop shop, InputStream shopImgInputStream, String fileName) {
        //获取shop图片目录的相对值路径
        String dest = Pathutil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generatorThumbnail(shopImgInputStream, dest, dest);
        shop.setShopImg(shopImgAddr);
    }
}
