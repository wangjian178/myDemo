package com.wj.demo.common.utils;

import com.wj.demo.common.model.vo.NodeVO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
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
     * @param paramList
     * @param fieldName       Id
     * @param parentFieldName parentId
     * @param <T>
     * @return
     */
    public static <T> List<NodeVO<T>> tree(List<T> paramList, String fieldName, String parentFieldName) {
        //最高级节点
        List<NodeVO<T>> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(paramList)) {
            return result;
        }

        //获取所有的字段
        Class<?> clazz = paramList.get(0).getClass();
        List<Field> fieldList = FieldUtils.getAllFields(clazz);
        Field field = fieldList.stream().filter(x -> x.getName().equals(fieldName)).findFirst().orElse(null);
        Field parentField = fieldList.stream().filter(x -> x.getName().equals(parentFieldName)).findFirst().orElse(null);
        if (field == null || parentField == null) {
            return result;
        }

        //所有的节点
        List<NodeVO<T>> allNodeVOList = new ArrayList<>(){{
            for (T t : paramList) {
                add(new NodeVO(t, new ArrayList<T>()));
            }
        }};

        //id与节点的对应关系
        Map<Object, NodeVO<T>> idAndNodeVOMap = allNodeVOList
                .stream()
                .filter(x -> FieldUtils.getFieldValue(field, x.getData()) != null)
                .collect(Collectors.toMap(x -> FieldUtils.getFieldValue(field, x.getData()), x -> x));

        //遍历所有节点设置到对应的父节点
        Iterator<NodeVO<T>> iterator = allNodeVOList.iterator();
        while (iterator.hasNext()) {
            NodeVO<T> nodeVO = iterator.next();
            T data = nodeVO.getData();

            Object parentId = FieldUtils.getFieldValue(parentField, data);
            if (parentId != null && idAndNodeVOMap.containsKey(parentId)) {
                //当前节点是子节点
                NodeVO<T> parentNodeVO = idAndNodeVOMap.get(parentId);
                List<NodeVO<T>> children = parentNodeVO.getChildren();
                children.add(nodeVO);
            } else {
                //当前节点是父节点
                result.add(nodeVO);
            }
        }

        return result;
    }
}
