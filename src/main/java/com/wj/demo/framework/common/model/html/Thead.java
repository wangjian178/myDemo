package com.wj.demo.framework.common.model.html;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName Thead
 * @Description: 表头
 * @Author: W.Jian
 * @CreateDate: 2025/3/13 11:23
 * @Version:
 */
@Getter
@Setter
public class Thead {
    /**
     * 默认样式
     */
    public static final String DEFAULT_STYLE = "";

    /**
     * 样式
     */
    private String style;

    /**
     * 表头集合
     */
    private List<Tr> trList;

    /**
     * 私有化构造方法
     */
    private Thead() {
    }

    /**
     * 构建方法
     *
     * @param trList tr标签
     * @param style  样式
     * @return Thead标签
     */
    public static Thead build(List<Tr> trList, String style) {
        Thead thead = new Thead();
        thead.setTrList(trList);
        thead.setStyle(Objects.requireNonNullElse(style, DEFAULT_STYLE));
        return thead;
    }
}
