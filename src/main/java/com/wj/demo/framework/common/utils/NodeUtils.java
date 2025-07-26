package com.wj.demo.framework.common.utils;

import com.wj.demo.framework.common.model.Node;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
                add(new Node<>(t, new ArrayList<>()));
            }
        }};

        //id与节点的对应关系
        Map<Object, Node<T>> idAndNodeVoMap = allNodeList
                .stream()
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

    /**
     * 树形结构
     *
     * @param paramList      数据
     * @param idGetter       Id
     * @param parentIdGetter parentId
     * @param <T>            数据类型
     */
    public static <T, K> List<Node<T>> tree(List<T> paramList, Function<T, K> idGetter, Function<T, K> parentIdGetter) {
        //最高级节点
        List<Node<T>> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(paramList)) {
            return result;
        }

        //所有的节点
        List<Node<T>> allNodeList = new ArrayList<>() {{
            for (T t : paramList) {
                add(new Node<>(t, new ArrayList<>()));
            }
        }};

        //id与节点的对应关系
        Map<K, Node<T>> nodeMap = allNodeList.stream().collect(Collectors.toMap(x -> idGetter.apply(x.getData()), Function.identity()));

        //遍历所有节点设置到对应的父节点
        for (Node<T> node : allNodeList) {
            //获取父节点id
            K parentId = parentIdGetter.apply(node.getData());
            //父级节点为null 则为顶级节点
            if (parentId == null || !nodeMap.containsKey(parentId)) {
                result.add(node);
                continue;
            }
            nodeMap.get(parentId).getChildren().add(node);
        }

        return result;
    }

    /**
     * 排序
     *
     * @param nodeList   树结构
     * @param sortGetter 排序字段
     * @param asc        是否升序
     * @param <T>        数据类型
     * @param <K>        排序字段类型
     */
    public static <T, K extends Comparable<K>> void sortTree(List<Node<T>> nodeList, Function<T, K> sortGetter, boolean asc) {
        if (CollectionUtils.isEmpty(nodeList) || sortGetter == null) {
            return;
        }
        //获取比较器
        Comparator<Node<T>> comparator = getComparator(sortGetter, asc);

        //根节点排序
        nodeList.sort(comparator);
        for (Node<T> node : nodeList) {
            //子节点排序
            if (CollectionUtils.isNotEmpty(node.getChildren())) {
                node.getChildren().sort(comparator);
            }
        }
    }

    /**
     * 获取排序器
     *
     * @param sortGetter 排序字段
     * @param asc        是否升序
     * @param <T>        数据类型
     * @param <K>        排序字段类型
     */
    private static <T, K extends Comparable<K>> Comparator<Node<T>> getComparator(Function<T, K> sortGetter, boolean asc) {
        Comparator<Node<T>> comparator = Comparator.comparing(
                node -> sortGetter.apply(node.getData()),
                Comparator.nullsLast(Comparator.naturalOrder())
        );
        if (!asc) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
