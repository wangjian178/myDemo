package com.wj.demo.framework.common.model.html;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName Tr
 * @Description: Tr
 * @Author: W.Jian
 * @CreateDate: 2025/3/13 11:21
 * @Version:
 */
@Getter
@Setter
public class Tr {
    /**
     * 默认表头样式
     */
    public static final String DEFAULT_HEAD_STYLE = "background-color: #00aaa7; color: #fff; height: 35px";
    /**
     * 默认表体样式
     */
    public static final String DEFAULT_BODY_STYLE = "color:#666; height: 30px";

    /**
     * 样式
     */
    private String style;

    /**
     * th集合
     */
    private List<Th> thList;

    /**
     * 私有构造器
     */
    private Tr() {
    }

    /**
     * 构建tr
     *
     * @param thList  th集合
     * @param style   样式
     * @return tr标签
     */
    public static Tr build(List<Th> thList, String style) {
        Tr tr = new Tr();
        tr.setThList(thList);
        tr.setStyle(Objects.requireNonNullElse(style, DEFAULT_BODY_STYLE));
        return tr;
    }
}
