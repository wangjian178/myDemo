package com.wj.demo.framework.common.utils;

import com.wj.demo.framework.common.model.Node;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/23 14:41
 */
public class NodeUtils {
    /**
     * 排列成树状
     *
     * @param paramList       数据
     * @param fieldName       Id
     * @param parentFieldName parentId
     */
    public static <T> List<Node<T>> tree(List<T> paramList, String fieldName, String parentFieldName) {
        //最高级节点
        List<Node<T>> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(paramList)) {
            return result;
        }

        //获取所有的字段
        Class<?> clazz = paramList.getFirst().getClass();
        List<Field> fieldList = FieldUtils.getAllFields(clazz);
        Field field = fieldList.stream().filter(x -> x.getName().equals(fieldName)).findFirst().orElse(null);
        Field parentField = fieldList.stream().filter(x -> x.getName().equals(parentFieldName)).findFirst().orElse(null);
        if (field == null || parentField == null) {
            return result;
        }

        //所有的节点
        List<Node<T>> allNodeList = new ArrayList<>() {{
            for (T t : paramList) {
                add(new Node<T>(t, new ArrayList<>()));
            }
        }};

        //id与节点的对应关系
        Map<Object, Node<T>> idAndNodeVoMap = allNodeList
                .stream()
                .filter(x -> FieldUtils.getFieldValue(field, x.getData()) != null)
                .collect(Collectors.toMap(x -> FieldUtils.getFieldValue(field, x.getData()), x -> x));

        //遍历所有节点设置到对应的父节点
        for (Node<T> node : allNodeList) {
            T data = node.getData();

            Object parentId = FieldUtils.getFieldValue(parentField, data);
            if (parentId != null && idAndNodeVoMap.containsKey(parentId)) {
                //当前节点是子节点
                Node<T> parentNode = idAndNodeVoMap.get(parentId);
                List<Node<T>> children = parentNode.getChildren();
                children.add(node);
            } else {
                //当前节点是父节点
                result.add(node);
            }
        }

        return result;
    }
}
