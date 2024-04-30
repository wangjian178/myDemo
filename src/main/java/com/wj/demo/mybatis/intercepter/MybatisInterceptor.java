package com.wj.demo.mybatis.intercepter;

import com.wj.demo.common.utils.FieldUtils;
import com.wj.demo.common.utils.SecurityUtils;
import com.wj.demo.mybatis.annotation.CreatedBy;
import com.wj.demo.mybatis.annotation.CreatedTime;
import com.wj.demo.mybatis.annotation.UpdatedBy;
import com.wj.demo.mybatis.annotation.UpdatedTime;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/25 17:48
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        // 获取 SQL 命令
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        //只判断新增和修改
        if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
            // 获取参数
            Object parameter = invocation.getArgs()[1];
            //批量操作时
            if (parameter instanceof MapperMethod.ParamMap) {
                MapperMethod.ParamMap map = (MapperMethod.ParamMap) parameter;
                Object obj = map.get("et");
                if (obj instanceof Collection<?>) {
                    List<?> list = (List<?>) obj;
                    if (list != null) {
                        for (Object o : list) {
                            setParameter(o, sqlCommandType);
                        }
                    }
                } else {
                    setParameter(parameter, sqlCommandType);
                }
            } else {
                setParameter(parameter, sqlCommandType);
            }
        }
        return invocation.proceed();
    }

    public void setParameter(Object parameter, SqlCommandType sqlCommandType) throws Throwable {
        Class<?> clazz = parameter.getClass();

        //获取所有的属性
        List<Field> allFields = FieldUtils.getAllFields(clazz);

        for (Field field : allFields) {
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                // insert 语句插入 createBy
                if (field.getAnnotation(CreatedBy.class) != null) {
                    field.setAccessible(true);
                    field.set(parameter, SecurityUtils.getUser().getUsername());
                }

                if (field.getAnnotation(CreatedTime.class) != null) {
                    // insert 语句插入 CreatedDate
                    field.setAccessible(true);
                    field.set(parameter, new Date());
                }
            }

            if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                if (field.getAnnotation(UpdatedBy.class) != null) {
                    // update 语句插入 updateBy
                    field.setAccessible(true);
                    field.set(parameter, SecurityUtils.getUser().getUsername());
                }
                if (field.getAnnotation(UpdatedTime.class) != null) {
                    // update 语句插入 UpdatedDate
                    field.setAccessible(true);
                    field.set(parameter, new Date());
                }
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
