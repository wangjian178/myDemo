package com.wj.demo.common.utils;

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
}
