package com.guohui.o2o.utils;

public class Pathutil {

    private static String separator = System.getProperty("file.separator");

    public static String getImgBaesPath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")){
            basePath = "F:/work/image/";
        }else{
            basePath = "/home/image/";
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }

    public static String getShopImagePath(long shopId){
        String imagePath = "/upload/item/shop/"+shopId+"/";
        return imagePath.replace("/",separator);
    }
}
