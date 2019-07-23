package com.guohui.o2o.dao.split;


import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Objects;
import java.util.Properties;


/**
 * 次拦截器去拦截的mybatis传递进来的sql信息 ，根据相应的信息选择不同的 DBType
 */
//增删改的操作会被封装到  method = "update" 中
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    //正则表达式
    private static final String REGEX =".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    //主要的拦截方法
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //判读当前是不是 事物的
        boolean transactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        //获取mybatis传过来的一些变量的参数
        Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement)objects[0];
        System.out.println("888888888888888888888888888888888888888888");

        String lookupKey  = DynamicDataSourceHolder. BD_MASTER;
        if (transactionActive){
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)){
                // SelectKey 为自增id查询主键（ SELECT_LAST_INSERT_ID 方法） 就使用主库
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
                    lookupKey = DynamicDataSourceHolder. BD_MASTER;

                }else {
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\r]"," ");
                    if (sql.matches(REGEX)){
                        lookupKey = DynamicDataSourceHolder. BD_MASTER;
                    }else {
                        lookupKey = DynamicDataSourceHolder. BD_SLAVE;
                    }
                }
            }
        }else {
            lookupKey = DynamicDataSourceHolder. BD_MASTER;
        }
        logger.debug("设置方法[{}] use [{}] strategy[{}],sqlCommanType[{}]."
                ,ms.getId(),lookupKey,ms.getSqlCommandType().name());
        DynamicDataSourceHolder.setDbType(lookupKey);
        return invocation.proceed();
    }

    //返回封装好的对象 返回本体还是代理
    @Override
    public Object plugin(Object target) {
        //在mybatis中的executor是支持了一系列的增删改查的操作的
        if (target instanceof Executor){
            //拦截包装后返回
            return Plugin.wrap(target,this);
        }else {
            //返回本体
            return target;
        }
    }

    //类初始化的时候做些配置
    @Override
    public void setProperties(Properties properties) {

    }
}
