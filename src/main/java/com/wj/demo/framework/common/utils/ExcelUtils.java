package com.wj.demo.framework.common.utils;

import cn.idev.excel.ExcelWriter;
import cn.idev.excel.FastExcel;
import cn.idev.excel.support.ExcelTypeEnum;
import cn.idev.excel.write.builder.ExcelWriterBuilder;
import cn.idev.excel.write.metadata.WriteSheet;
import cn.idev.excel.write.metadata.style.WriteCellStyle;
import cn.idev.excel.write.metadata.style.WriteFont;
import cn.idev.excel.write.style.HorizontalCellStyleStrategy;
import cn.idev.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import cn.idev.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.wj.demo.framework.excel.ExcelContext;
import com.wj.demo.framework.excel.entity.Excel;
import com.wj.demo.framework.excel.entity.Sheet;
import com.wj.demo.framework.excel.entity.TableRow;
import com.wj.demo.framework.excel.listener.FastExcelListener;
import com.wj.demo.framework.exception.exception.BaseException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName ExcelUtils
 * @Description: Excel工具类
 * @Author: W.Jian
 * @CreateDate: 2025/8/16 17:48
 * @Version:
 */
public class ExcelUtils {

    /**
     * 读取Excel
     *
     * @param inputStream 输入流
     * @param excelName   Excel名称
     * @return Excel
     */
    public static Excel read(InputStream inputStream, String excelName) {
        // 初始化ExcelContext
        ExcelContext.init();
        // 获取Excel
        Excel excel = ExcelContext.get();
        excel.setName(excelName);
        // 读取Excel
        FastExcel.read(inputStream).registerReadListener(new FastExcelListener<>()).doReadAll();
        // 移除线程变量
        ExcelContext.remove();
        return excel;
    }

    /**
     * 读取Excel
     *
     * @param file 文件
     * @return Excel
     */
    public static Excel read(MultipartFile file) {
        // 读取Excel
        try (InputStream inputStream = file.getInputStream()) {
            return read(inputStream, file.getOriginalFilename());
        } catch (IOException e) {
            throw new BaseException(e);
        }
    }

    /**
     * 写入Excel
     *
     * @param excel    Excel
     * @param response 响应
     */
    public static void write(Excel excel, HttpServletResponse response) {
        // excel名称
        String excelName = java.net.URLEncoder.encode(Optional.ofNullable(excel.getName()).orElseGet(() -> "" + System.currentTimeMillis()) + ExcelTypeEnum.XLSX.getValue(), StandardCharsets.UTF_8);

        // 清除可能存在的头信息
        response.reset();
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + excelName);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setHeader(HttpHeaders.EXPIRES, "0");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            write(excel, outputStream);
        } catch (IOException e) {
            throw new BaseException(e);
        }
    }

    /**
     * 写入Excel
     *
     * @param excel        Excel
     * @param outputStream 输出流
     */
    public static void write(Excel excel, OutputStream outputStream) {
        ExcelWriterBuilder writerBuilder = FastExcel.write(outputStream);
        ExcelWriter writer = writerBuilder.build();
        List<Sheet> sheetList = excel.getSheetList();
        sheetList.forEach(sheet -> {

            // 表头
            List<List<String>> header = new ArrayList<>();
            sheet.getHeader().forEach(headerItem -> {
                header.add(Collections.singletonList(headerItem.getTitle()));
            });

            //  数据
            List<List<Object>> dataList = sheet.getBody().stream().map(TableRow::getCells).toList();

            // 创建WriteSheet
            WriteSheet writeSheet = writerBuilder
                    .sheet(sheet.getSheetName())
                    .head(header)
                    .registerWriteHandler(defaultWidthWriteHandler())
                    .registerWriteHandler(defaultHeightWriteHandler())
                    .registerWriteHandler(defaultWriteHandler())
                    .build();

            // 写入数据
            writer.write(dataList, writeSheet);
        });

        // 完成
        writer.finish();
    }

    /**
     * 默认宽度处理器
     *
     * @return 样式处理器
     */
    public static SimpleColumnWidthStyleStrategy defaultWidthWriteHandler() {
        return new SimpleColumnWidthStyleStrategy(20);
    }

    /**
     * 默认样式处理器
     * @return 样式处理器
     */
    public static SimpleRowHeightStyleStrategy defaultHeightWriteHandler() {
        return new SimpleRowHeightStyleStrategy(Short.valueOf("25"), Short.valueOf("15"));
    }

    /**
     * 默认样式处理器
     *
     * @return 样式处理器
     */
    public static HorizontalCellStyleStrategy defaultWriteHandler() {
        return new HorizontalCellStyleStrategy(headCellStyle(), contentCellStyle());
    }

    /**
     * 创建内容单元格样式
     *
     * @return CellStyle
     */
    public static WriteCellStyle headCellStyle() {
        // 表头样式（加粗+居中）
        WriteCellStyle headCellStyle = new WriteCellStyle();
        // 设置表头居中
        headCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 设置表头居中
        headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置表头背景颜色
        headCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // 设置表头填充
        headCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 表头字体
        WriteFont font = new WriteFont();
        // 宋体
        font.setFontName("宋体");
        // 表头加粗
        font.setBold(true);
        // 字体大小
        font.setFontHeightInPoints(Short.valueOf("12"));
        headCellStyle.setWriteFont(font);
        return headCellStyle;
    }

    /**
     * 创建内容单元格样式
     *
     * @return CellStyle
     */
    public static WriteCellStyle contentCellStyle() {
        WriteCellStyle contentCellStyle = new WriteCellStyle();
        // 水平居中
        contentCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 自动换行
        //contentCellStyle.setWrapped(true);
        // 单元格字体
        WriteFont font = new WriteFont();
        // 宋体
        font.setFontName("宋体");
        // 字体大小
        font.setFontHeightInPoints(Short.valueOf("12"));
        contentCellStyle.setWriteFont(font);
        return contentCellStyle;
    }

}
