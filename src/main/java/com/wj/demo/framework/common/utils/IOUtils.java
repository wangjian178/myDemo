package com.wj.demo.framework.common.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName IOUtils
 * @Description  io流工具类
 * @Author W.Jian
 * @Date 2021/7/6 9:45
 **/
public class IOUtils {

    public static void main(String[] args) {
        writeToFile("lalala","C:\\Users\\W.Jian\\Desktop\\新建文本文档.txt");
    }

    /**
     * 从文件中读取文本
     * @param filePath
     * @return
     */
    public static String readFromFile(String filePath){
        if(filePath == null || filePath.equals("")){
            System.out.println("路径为空！");
            return "路径不能为空！";
        }
        File file = new File(filePath);
        if(!file.exists()){
            System.out.println("目标文件不存在！");
            return "目标文件不存在！";
        }
        StringBuilder builder = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            //定义char数组
            char[] data = new char[23];

            int length = 0;
            //循环读取文件中的数据
            while((length = fr.read(data))>0){
                //根据读取文件的内容创建String 对象
                String str = new String(data,0,length);
                //输出读取内容
                builder.append(str);
            }
            //关闭流
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * 将文本写入文件
     * @param filePath
     * @return
     */
    public static String writeToFile(String content,String filePath){
        if(content == null){
            return "内容为空！";
        }
        if(filePath == null || filePath.equals("")){
            return "路径不能为空！";
        }
        //创建指定文件
        File file = new File(filePath);
        try {
            if(! file.exists()){
                //如果指定文件不存在，新建文件
                System.out.println("文件不存在，新建文件。。。");
                file.createNewFile();
            }
            //创建FileWriter对象
            FileWriter fw = new FileWriter(file);
            //向文件写入数据
            fw.write(content.toCharArray());
			//关闭流
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }

    /**
     * 从一个文件读取到另一个文件
     * @param source
     * @param target
     * @return
     */
    public static String readFromSourceAndWriteToTarget(String source,String target){
        if(StringUtils.isEmpty(source) || StringUtils.isEmpty(target)){
            return "路径不能为空！";
        }
        //创建指定文件
        File file = new File(source);
        if(! file.exists()){
            //如果指定文件不存在，新建文件
            System.out.println("来源文件不存在");
            return "来源文件不存在！";
        }

        try {
            File targetFile = new File(target);
            if(! targetFile.exists()){
                System.out.println("目标文件不存在，正在创建");
                targetFile.createNewFile();
            }
            //创建FileWriter对象
            FileWriter fw = new FileWriter(file);
            //向文件写入数据
            //fw.write(content.toCharArray());
            //关闭流
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }
}
