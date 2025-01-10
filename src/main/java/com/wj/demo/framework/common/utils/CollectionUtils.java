package com.wj.demo.framework.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/23 11:21
 */
public class CollectionUtils {

    /**
     * 创建新集合
     *
     * @return
     */
    public static List newList() {
        return new ArrayList<>();
    }

    /**
     * 是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 是否不为空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 分页
     *
     * @param list
     * @param pageNum
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<T> subList(List<T> list, int pageNum, int pageSize) {
        // 总记录数
        int count = list.size();
        // 计算总页数
        int pages = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        // 起始位置
        int start = pageNum <= 0 ? 0 : (pageNum > pages ? (pages - 1) * pageSize : (pageNum - 1) * pageSize);
        // 终止位置
        int end = pageNum <= 0 ? (pageSize <= count ? pageSize : count) : (pageSize * pageNum <= count ? pageSize * pageNum : count);
        return list.subList(start, end);
    }

    /**
     * 拆分
     *
     * @param list
     * @return
     */
    public static <T> List<List<T>> split(List<T> list, Integer length) {
        List<List<T>> result = new ArrayList<>();

        //1.空集合直接返回
        if (isEmpty(list)) {
            return result;
        }

        //2.size<=length
        int size = list.size();
        if (size <= length) {
            result.add(list);
            return result;
        }

        //3.size>length
        int newSize = size % length == 0 ? size / length : size / length + 1;
        for (int i = 1; i <= newSize; i++) {
            result.add(subList(list, i, length));
        }

        return result;
    }

    /**
     * 拆分
     *
     * @param list
     * @return
     */
    public static <T> List<List<T>> split(List<T> list) {
        return split(list, 1000);
    }
}
