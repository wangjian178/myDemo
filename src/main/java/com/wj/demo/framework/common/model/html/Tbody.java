package com.wj.demo.framework.common.model.html;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName Tbody
 * @Description: 表格内容
 * @Author: W.Jian
 * @CreateDate: 2025/3/13 11:26
 * @Version:
 */
@Getter
@Setter
public class Tbody {
    /**
     * 默认样式
     */
    public static final String DEFAULT_STYLE = "";

    /**
     * 表格样式
     */
    private String style;
    /**
     * 表格内容
     */
    private List<Tr> trList;

    /**
     * 私有化构造器
     */
    private Tbody() {
    }

    /**
     * 构建表格内容
     * @param trList tr标签
     * @param style 样式
     * @return Tbody标签
     */
    public static Tbody build(List<Tr> trList, String style) {
        Tbody tbody = new Tbody();
        tbody.setTrList(trList);
        tbody.setStyle(Objects.requireNonNullElse(style, DEFAULT_STYLE));
        return tbody;
    }
}
