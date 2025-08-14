package com.wj.demo.framework.common.utils;

import com.wj.demo.framework.exception.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName IOUtils
 * @Description io流工具类
 * @Author W.Jian
 * @Date 2021/7/6 9:45
 **/
@Slf4j
public class IOUtils {

    /**
     * 从文件中读取文本
     *
     * @param filePath 路径
     * @return 文本
     */
    public static String readFromFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new BusinessException("文件路径不能为空！");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new BusinessException("目标文件不存在！");
        }
        if (!file.canRead()) {
            throw new BusinessException("目标文件不可读！");
        }
        StringBuilder builder = new StringBuilder();
        try (FileReader fr = new FileReader(file)) {
            //定义char数组
            char[] data = new char[23];

            int length = 0;
            //循环读取文件中的数据
            while ((length = fr.read(data)) > 0) {
                //根据读取文件的内容创建String 对象
                String str = new String(data, 0, length);
                //输出读取内容
                builder.append(str);
            }
        } catch (Exception e) {
            log.error("文件读取失败", e);
            throw new BusinessException("文件读取失败");
        }
        log.info("文件读取成功。。。{}", builder);
        return builder.toString();
    }

    /**
     * 将文本写入文件
     *
     * @param content  文本
     * @param filePath 路径
     */
    public static void writeToFile(String content, String filePath) {
        if (content == null) {
            return;
        }
        if (filePath == null || filePath.isEmpty()) {
            return;
        }
        //创建指定文件
        File file = new File(filePath);
        if (!file.exists()) {
            //如果指定文件不存在，新建文件
            try {
                boolean createStatus = file.createNewFile();
                log.info("文件不存在，新建文件。。。{}  {}", filePath, createStatus);
            } catch (IOException e) {
                log.error("新建文件失败", e);
                throw new BusinessException("新建文件失败");
            }
        }
        if (!file.canWrite()) {
            throw new BusinessException("文件不可写入");
        }
        //创建FileWriter对象
        try (FileWriter fw = new FileWriter(file)) {
            //向文件写入数据
            fw.write(content);
        } catch (IOException e) {
            log.error("文件写入失败", e);
        }
        log.info("文件写入成功。。。{}", filePath);
    }

    /**
     * 从一个文件读取到另一个文件
     *
     * @param source 路径
     * @param target 目标
     */
    public static void readFromSourceAndWriteToTarget(String source, String target) {
        if (StringUtils.isEmpty(source)) {
            throw new BusinessException("来源路径不能为空！");
        }
        if (StringUtils.isEmpty(target)) {
            throw new BusinessException("目标路径不能为空！");
        }
        //创建指定文件
        File file = new File(source);
        if (!file.exists()) {
            //如果指定文件不存在，新建文件
            throw new BusinessException("来源文件不存在！");
        }

        try {
            File targetFile = new File(target);
            if (!targetFile.exists()) {
                boolean createStatus = targetFile.createNewFile();
                log.info("目标文件不存在，正在创建。。。{}", createStatus);
            }
            //创建FileWriter对象
            FileWriter fw = new FileWriter(targetFile);
            //向文件写入数据
            fw.write(readFromFile(source));
            //关闭流
            fw.close();
        } catch (IOException e) {
            log.error("文件读取写入失败", e);
        }
        log.info("文件读取写入成功。。。{}", target);
    }
}
