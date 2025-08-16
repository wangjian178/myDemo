package com.wj.demo.framework.excel.listener;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.read.listener.ReadListener;
import com.wj.demo.framework.excel.ExcelContext;
import com.wj.demo.framework.excel.entity.Sheet;
import com.wj.demo.framework.excel.entity.TableHeader;
import com.wj.demo.framework.excel.entity.TableRow;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EasyExcelListener
 * @Description: EasyExcel监听处理
 * @Author: W.Jian
 * @CreateDate: 2025/8/16 17:10
 * @Version:
 */
@Slf4j
public class FastExcelListener<T> implements ReadListener<T> {

    /**
     * 表头
     *
     * @param headMap 表头
     * @param context 上下文
     */
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        List<Sheet> sheetList = ExcelContext.get().getSheetList();
        if (sheetList == null) {
            sheetList = new ArrayList<>();
        }
        List<TableHeader> headers = new ArrayList<>();
        headMap.forEach((key, value) -> {
            TableHeader header = new TableHeader().setTitle(value.getStringValue());
            headers.add(header);
        });
        Sheet sheet = new Sheet()
                .setSheetIndex(context.readSheetHolder().getSheetNo())
                .setSheetName(context.readSheetHolder().getSheetName())
                .setHeader(headers);
        sheetList.add(sheet);
        ExcelContext.get().setSheetList(sheetList);
    }

    /**
     * 读取数据
     *
     * @param t               每行实体
     * @param analysisContext 上下文
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        Sheet sheet = ExcelContext.get().getSheetList().getLast();
        List<TableRow> body = sheet.getBody();
        if (body == null) {
            body = new ArrayList<>();
        }
        if (t instanceof LinkedHashMap<?, ?>) {
            TableRow tableRow = new TableRow().setCells(new ArrayList<Object>(((LinkedHashMap<?, ?>) t).values()));
            body.add(tableRow);
        }
        sheet.setBody(body);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("数据解析完成----------------------------------");
    }
}
