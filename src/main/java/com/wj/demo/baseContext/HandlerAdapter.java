package com.wj.demo.baseContext;

import com.wj.demo.common.annotation.FieldOrder;
import com.wj.demo.common.constant.BaseConstant;
import com.wj.demo.common.utils.FieldUtils;
import com.wj.demo.common.utils.SpringContextUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wj
 * @version 1.0
 * @Desc 寻找对应的处理器 todo 是否可以在所有的manager层或者service调用之前加切面处理 或者 做一个拦截转发
 * @date 2024/4/18 15:00
 */
public class HandlerAdapter {


    /**
     * todo 方式一 产品层接口与差异层接口单独实现 只产品层做转发     差异层直接注入差异的service调用
     * todo 方式二 产品层接口与差异层接口单独实现 全部统一转发       不再注入，而是通过bean+method+args直接调用
     *
     * 差异层需要继承产品层业务，做一个扩展增强
     * 方式二 太智能了难以实现，同层调用不好实现，无法自动获取具体方法，需要手动输入，这样的话失去了简单转发的意义，不如方法一
     *
     * 四层架构 controller、【abstractManager（抽象）、decoratorManager(implements abstractManager 转发)、manager（implements abstractManager 业务）】、service（小业务）、dao（数据库）
     * 三层架构 controller、【service（业务，包含用于实现业务的私有化小业务）、serviceImpl（具体实现）】、dao（数据库）
     *
     * 相对成熟的方式为方式一+四层架构
     */


    /**
     * 类型获取处理器
     * 按照上下文条件，依次获取处理器，返回得到的第一个，默认处理器优先同名，其次default
     *
     * @param clazz
     */
    public static <T> T finderHandler(Class<T> clazz) {

        //  1.获取所有参与寻找处理的属性并且排序
        List<Field> allFields = FieldUtils.getAllFields(BaseContext.class);
        //  过滤 排序
        List<Field> sortFields = allFields
                .stream()
                .filter(x -> x.getAnnotation(FieldOrder.class) != null)
                .sorted(Comparator.comparingInt(x -> x.getAnnotation(FieldOrder.class).value()))
                .collect(Collectors.toList());

        //  2.获取上下文值
        BaseContext baseContext = BaseContextHolder.getBaseContext();

        //  3.组成名字
        String appendName = sortFields
                .stream()
                .filter(x -> FieldUtils.getFieldValue(x, baseContext) != null)
                .map(x -> FieldUtils.getFieldValue(x, baseContext).toString())
                .collect(Collectors.joining(BaseConstant.SLASH));
        String beanName = clazz.getSimpleName() + (BaseConstant.SLASH + appendName);

        //  4.寻找处理器
        T bean = SpringContextUtils.getBean(beanName, clazz);
        while (bean == null) {
            //继续截取
            boolean next = beanName.contains(BaseConstant.SLASH);
            beanName = next ? beanName.substring(0, beanName.lastIndexOf(BaseConstant.SLASH)) : beanName + BaseConstant.SLASH + BaseConstant.DEFAULT;
            bean = SpringContextUtils.getBean(beanName, clazz);
            if (!next) {
                break;
            }
        }

        return bean;
    }

    /**
     * 类型 名称条件 获取处理器
     * 按照上下文条件，依次获取处理器，返回得到的第一个，默认处理器优先同名，其次default
     *
     * @param clazz
     */
    public static <T> T finderHandler(Class<T> clazz, List<String> fieldNameList) {

        //  1.获取所有参与寻找处理的属性并且排序
        List<Field> allFields = FieldUtils.getAllFields(BaseContext.class);
        //  过滤 排序
        List<Field> sortFields = allFields
                .stream()
                .filter(x -> fieldNameList.contains(x.getName()))
                .sorted(Comparator.comparingInt(x -> fieldNameList.indexOf(x.getName())))
                .collect(Collectors.toList());

        //  2.获取上下文值
        BaseContext baseContext = BaseContextHolder.getBaseContext();

        //  3.组成名字
        String appendName = sortFields
                .stream()
                .filter(x -> FieldUtils.getFieldValue(x, baseContext) != null)
                .map(x -> FieldUtils.getFieldValue(x, baseContext).toString())
                .collect(Collectors.joining(BaseConstant.SLASH));
        String beanName = clazz.getSimpleName() + (BaseConstant.SLASH + appendName);

        //  4.寻找处理器
        T bean = SpringContextUtils.getBean(beanName, clazz);
        while (bean == null) {
            //继续截取
            boolean next = beanName.contains(BaseConstant.SLASH);
            beanName = next ? beanName.substring(0, beanName.lastIndexOf(BaseConstant.SLASH)) : beanName + BaseConstant.SLASH + BaseConstant.DEFAULT;
            bean = SpringContextUtils.getBean(beanName, clazz);
            if (!next) {
                break;
            }
        }

        return bean;
    }
}
