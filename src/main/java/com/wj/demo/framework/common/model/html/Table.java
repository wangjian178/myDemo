package com.wj.demo.framework.common.model.html;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @ClassName Table
 * @Description: Table表格
 * @Author: W.Jian
 * @CreateDate: 2025/3/13 11:20
 * @Version:
 */
@Getter
@Setter
public class Table {

    /**
     * 默认样式 默认合并表格线collapse，字体居中
     */
    public static final String DEFAULT_STYLE = "border-collapse: collapse; border-color: #666; text-align: center; width: 100%;";

    /**
     * 默认边框
     */
    public static final Integer DEFAULT_BORDER = 1;

    /**
     * 标题
     */
    private String caption;

    /**
     * 边框
     */
    private Integer border;

    /**
     * 表头
     */
    private Thead thead;

    /**
     * 表体
     */
    private Tbody tbody;

    /**
     * 表格样式
     */
    private String style;

    /**
     * 单元格间距
     */
    private int cellPadding;

    /**
     * 单元格间距
     */
    private int cellSpacing;

    /**
     * 私有化构造器
     */
    private Table() {
    }

    /**
     * 构建表格
     *
     * @param caption 标题
     * @param thead   标题
     * @param tbody   表体
     * @param style   格式
     * @return Table标签
     */
    public static Table build(String caption, Thead thead, Tbody tbody, Integer border, String style) {
        Table table = new Table();
        table.setCaption(caption);
        table.setThead(thead);
        table.setTbody(tbody);
        table.setBorder(border);
        table.setStyle(Objects.requireNonNullElse(style, DEFAULT_STYLE));
        return table;
    }
}
