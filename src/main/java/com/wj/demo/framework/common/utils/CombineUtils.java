package com.wj.demo.framework.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author W.Jian
 * @version 1.0
 * @Desc 组合工具类
 * @date 2024/11/5 16:15
 */
public class CombineUtils<T> {

    /**
     * 获取所有的排列方式
     *
     * @param input 输入
     */
    public static <T> List<List<T>> getPermutations(List<T> input) {
        //所有结果
        List<List<T>> result = new ArrayList<>();

        //剩余元素
        List<T> restElements = new ArrayList<>(input);

        //记录当前排列元素
        List<T> recordElements = new ArrayList<>(input.size());

        permutation(recordElements, restElements, result);

        return result;
    }

    /**
     * 排列
     *
     * @param recordElements 记录的元素
     * @param restElements   剩余的元素
     * @param result         结果
     * @param <T>            泛型
     */
    public static <T> void permutation(List<T> recordElements,
                                       List<T> restElements,
                                       List<List<T>> result) {
        for (T element : restElements) {
            //保持记录元素+剩余元素=全部元素
            recordElements.removeIf(restElements::contains);

            recordElements.add(element);

            if (restElements.size() == 1) {
                result.add(new ArrayList<>(recordElements));
                continue;
            }
            //新的剩余元素
            List<T> newRestElements = new ArrayList<>(restElements);
            newRestElements.remove(element);
            permutation(recordElements, newRestElements, result);
        }
    }

    /**
     * 获取所有组合
     *
     * @param input 输入 [{1,2},{3,4}]
     * @return 结果[{1,3},{1,4},{2,3},{2,4}]
     */
    public static <T> List<List<T>> getCombines(List<List<T>> input) {
        //所有结果
        List<List<T>> result = new ArrayList<>();
        //记录当前索引
        List<T> recordLine = new ArrayList<>(input.size());
        combine(input, result, recordLine, 0);
        return result;
    }

    /**
     * 组合
     *
     * @param input      输入
     * @param result     结果
     * @param recordLine 记录的线体
     * @param planIndex  计划索引
     */
    private static <T> void combine(
            List<List<T>> input,
            List<List<T>> result,
            List<T> recordLine,
            Integer planIndex) {
        //当前所有线体
        List<T> thisLineList = input.get(planIndex);

        for (T t : thisLineList) {
            //删除本条计划和之后的计划
            recordLine.removeIf(line -> recordLine.indexOf(line) >= planIndex);
            //记录当前线体
            recordLine.add(t);

            if (planIndex == input.size() - 1) {
                //最后一条计划，记录结果
                List<T> combine = new ArrayList<>(recordLine);
                result.add(combine);
            } else if (planIndex < input.size() - 1) {
                //不是最后一条计划，继续递归
                combine(input, result, recordLine, planIndex + 1);
            }
        }
    }
}
