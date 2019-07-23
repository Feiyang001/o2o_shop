package com.guohui.o2o.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 图片处理的工具类
 */

public class ImageUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    private static String basePath  = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat smp = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
    private static final Random r = new Random();


    /**
     * 将CommonsMultipartFile 转化为File
     * @param commonsMultipartFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile commonsMultipartFile){
        File file = new File(commonsMultipartFile.getOriginalFilename());
        try {
            commonsMultipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 出来缩略图 并返回新生产图片的相对值路径
     * @param targetAddr
     * @return
     */
    public static String generatorThumbnail(InputStream thumbnailInputStream, String targetAddr,String fileName){
        //得到一个绝对路径
        String realFileName  = getRandomFileName();
        //获得文件的扩展名
        String extension = getFileExtension(fileName);
        //创建目标文件的文件夹
        makeDirPath(targetAddr);
        //相对路径
        String relativeAddr = targetAddr+realFileName+extension;
        logger.debug("current relativeAddr is "+relativeAddr);
        File  dest = new File(Pathutil.getImgBaesPath()+relativeAddr);
        logger.debug("current realAddr is "+dest);
        try {
            basePath = URLDecoder.decode(basePath,"utf-8");//这样就能解决掉那个空格(路径中的%20)的问题了。
            Thumbnails.of(thumbnailInputStream).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(
                    new File(basePath+"/watermark.jpg")),0.25f).outputQuality(0.8f)
                    .toFile(dest);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString());
        }
        return relativeAddr;
    }

    /**
     * 创建木标路径所涉及的目录，如果路径所包含的文件夹不存在 得自动创建出来
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        //全路径
        String realFilePath = Pathutil.getImgBaesPath()+targetAddr;
        File dirPath = new File(realFilePath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * 获取文件的扩展名
     */
    private static String getFileExtension(String fileName) {
        //originalFilename.lastIndexOf(".") 最后一个 . 号后面的字符
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 随机生成文件名  当前的年月日时分秒 +五位的随机数
     * @return
     */
    private static String getRandomFileName() {
        //获取随机的五位数
        int rannum = r.nextInt(89999)+10000;//大于10000小于99999；
        String nowTimeStr = smp.format(new Date());
        return nowTimeStr+rannum;
    }

    public static void main(String[] args) throws IOException {
        //System.out.println(basePath);
        basePath = URLDecoder.decode(basePath,"utf-8");//这样就能解决掉那个空格(路径中的%20)的问题了。
        Thumbnails.of(new File("C:Users/Administrator/Desktop/Resources/gouI.jpg"))
                .size(200,200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(
                        new File(basePath+"/watermark.jpg")),0.25f).outputQuality(0.8f)
                .toFile("C:Users/Administrator/Desktop/Resources/newgouI.jpg");
    }

    /**
     * 如果 storPath 是文件路径 则删除文件
     * 是目录 删除该目录下的所有的文件
     * @param storPath 文件路径或者目录的路径
     */

    public static void deleteFileOrPath(String storPath){
        File filePath = new File(Pathutil.getImgBaesPath()+storPath);
        if (filePath.exists()){
            if (filePath.isDirectory()){
                File[] files = filePath.listFiles();
                for (int i=0;i<files.length;i++) {
                    files[i].delete();
                }
            }
            filePath.delete();
        }
    }

}
