package com.wj.demo.framework.common.utils;

import com.wj.demo.framework.common.model.html.*;

import java.util.List;
import java.util.Objects;

/**
 * @ClassName H5Utils
 * @Description: h5工具类
 * @Author: W.Jian
 * @CreateDate: 2025/2/28 16:53
 * @Version: 1.0
 */
public class HtmlUtils {

    /**
     * 创建表格
     *
     * @param table 表格
     * @return html标签代码
     */
    public static String createTable(Table table) {
        StringBuilder builder = new StringBuilder();
        builder.append("<table  cellspacing=\"").append(table.getCellSpacing()).append("\" cellpadding=\"").append(table.getCellPadding()).append("\" style=\"").append(table.getStyle()).append("\">");
        if (table.getCaption() != null) {
            builder.append("<caption>").append(table.getCaption()).append("</caption>");
        }
        builder.append(createTableHeader(table.getThead()));
        builder.append(createTableBody(table.getTbody()));
        builder.append("</table>");
        return builder.toString();
    }

    /**
     * 创建表头
     *
     * @param thead 表头
     * @return html
     */
    public static String createTableHeader(Thead thead) {
        return "<thead style=\"" + thead.getStyle() + "\">" + createTableTr(thead.getTrList()) + "</thead>";
    }

    /**
     * 创建表体
     *
     * @param tbody 表体
     * @return html
     */
    public static String createTableBody(Tbody tbody) {
        return "<tbody style=\"" + tbody.getStyle() + "\">" + createTableTr(tbody.getTrList()) + "</tbody>";
    }

    /**
     * 创建表格行
     *
     * @param trList 行集合
     * @return html
     */
    public static String createTableTr(List<Tr> trList) {
        StringBuilder builder = new StringBuilder();
        if (CollectionUtils.isEmpty(trList)) {
            return builder.toString();
        }
        for (Tr tr : trList) {
            builder.append("<tr style=\"").append(tr.getStyle()).append("\">");
            for (Th th : tr.getThList()) {
                builder.append("<th colspan=\"").append(th.getColspan()).append("\" rowspan=\"").append(th.getRowspan()).append("\" style=\"").append(th.getStyle()).append("\">");
                builder.append(Objects.requireNonNullElse(th.getContent(), ""));
                builder.append("</th>");
            }
            builder.append("</tr>");
        }
        return builder.toString();
    }
}
