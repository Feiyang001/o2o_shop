package com.guohui.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceHolder {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);

    //用来报存我们的key
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static final String BD_MASTER = "master";

    public static final String BD_SLAVE = "slave";

    public static String getDbType(){
        String db = contextHolder.get();
        if (db == null){
            db = BD_MASTER;
        }
        return db;
    }
    //设置线程的DbType
    public static void setDbType(String str){
        logger.debug("所使用的数据源是："+str);

        contextHolder.set(str);
    }
    //清理连接类型
    public static void clearBDType(){
        contextHolder.remove();
    }



}
