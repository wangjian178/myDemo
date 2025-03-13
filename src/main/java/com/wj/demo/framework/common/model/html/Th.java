package com.wj.demo.framework.common.model.html;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @ClassName Th
 * @Description:
 * @Author: W.Jian
 * @CreateDate: 2025/3/13 11:20
 * @Version:
 */
@Getter
@Setter
public class Th {
    /**
     * 默认表头样式
     */
    public static final String DEFAULT_STYLE = "text-align: center;";
    /**
     * 默认合并
     */
    public static final Integer DEFAULT_MERGE = 1;

    /**
     * 文本
     */
    private String content;

    /**
     * 行合并
     */
    private Integer rowspan;
    /**
     * 列合并
     */
    private Integer colspan;

    /**
     * 样式
     */
    private String style;

    /**
     * 私有化构造器
     */
    private Th() {
    }

    /**
     * 构建
     *
     * @param content 内容
     * @param rowspan 行合并
     * @param colspan 列合并
     * @param style   样式
     * @return Th标签
     */
    public static Th build(String content, Integer rowspan, Integer colspan, String style) {
        Th th = new Th();
        th.setContent(content);
        th.setRowspan(Objects.requireNonNullElse(rowspan, DEFAULT_MERGE));
        th.setColspan(Objects.requireNonNullElse(colspan, DEFAULT_MERGE));
        th.setStyle(Objects.requireNonNullElse(style, DEFAULT_STYLE));
        return th;
    }
}
