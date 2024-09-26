package com.wj.demo.sqlGenerator;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wj.demo.common.utils.FieldUtils;
import com.wj.demo.i18n.entity.SysLanguageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author W.Jian
 * @version 1.0
 * @Desc 初始化数据库
 * @date 2024/9/25 11:37
 */
public class SQLGenerator {

    /**
     * JavaType和JdbcType对应关系
     */
    private static final Map<Class, JdbcType> JAVA_TYPE_AND_JDBC_TYPE = new HashMap<>() {{
        put(String.class, JdbcType.VARCHAR);

        put(Integer.class, JdbcType.INTEGER);
        put(Long.class, JdbcType.BIGINT);
        put(Float.class, JdbcType.FLOAT);
        put(Double.class, JdbcType.DOUBLE);

        put(Boolean.class, JdbcType.INTEGER);

        put(Date.class, JdbcType.TIMESTAMP);
        put(LocalDate.class, JdbcType.DATE);
        put(LocalDateTime.class, JdbcType.TIMESTAMP);
    }};


    public static void main(String[] args) {
        createTable();
    }

    /**
     * 表生成
     */
    public static void createTable() {
        //1.扫描@TableName所有的类
        List<Class<?>> classes = List.of(SysLanguageEntity.class);//findAllClasses();
        //2.删除所有的表
        String dropSql = classes.stream().map(SQLGenerator::generateSqlForDrop).collect(Collectors.joining("\n\n"));
        //3.新增所有的表
        String createSql = classes.stream().map(SQLGenerator::generateSqlForCreate).collect(Collectors.joining("\n\n"));
        //4.执行初始化数据sql
        System.out.println(dropSql);
        System.out.println(createSql);

/*        SqlSession session = sqlSessionFactory.openSession();
        session.update(dropSql);
        session.commit();*/
    }

    /**
     * 建表sql
     *
     * @param clazz
     */
    public static String generateSqlForCreate(Class<?> clazz) {
        //获取表名
        TableName tableNameAnnotation = clazz.getAnnotation(TableName.class);
        String tableName = tableNameAnnotation.value().isEmpty() ? clazz.getSimpleName() : tableNameAnnotation.value();
        //表注释
        Schema tableSchema = clazz.getAnnotation(Schema.class);
        String tableComment = tableSchema == null ? "" : tableSchema.description();

        StringBuilder createSql = new StringBuilder("CREATE TABLE IF NOT EXISTS `" + tableName + "` (\n");

        List<Field> fields = FieldUtils.getAllFields(clazz);
        for (Field field : fields) {

            //字段名
            String columnName = null;
            //字段类型
            JdbcType jdbcType = null;
            //字段长度
            Integer length = Integer.valueOf(255);

            //获取字段名/字段类型
            TableId idAnnotation = field.getAnnotation(TableId.class);
            if (idAnnotation != null) {
                columnName = idAnnotation.value();
                jdbcType = JdbcType.BIGINT;
            }
            TableField fieldAnnotation = field.getAnnotation(TableField.class);
            if (fieldAnnotation != null && fieldAnnotation.exist()) {
                columnName = fieldAnnotation.value();
                jdbcType = fieldAnnotation.jdbcType();
            }
            if (idAnnotation == null && fieldAnnotation == null) {
                continue;
            }
            //根据Java类型获取
            if (jdbcType == JdbcType.UNDEFINED) {
                jdbcType = JAVA_TYPE_AND_JDBC_TYPE.get(field.getType());
            }
            //字段注释
            Schema fieldSchema = field.getAnnotation(Schema.class);
            String fieldComment = fieldSchema == null ? "" : fieldSchema.description();

            String columnType = jdbcType.name();
            String columnLength = JdbcType.VARCHAR.equals(jdbcType) ? "(" + length + ")" : "";

            createSql.append("  `")
                    .append(columnName)
                    .append("` ")
                    .append(columnType)
                    .append(columnLength)
                    .append(idAnnotation == null ? "" : " PRIMARY KEY AUTO_INCREMENT")
                    .append("  COMMENT '").append(fieldComment).append("'")
                    .append(",\n");
        }

        if (!createSql.isEmpty()) {
            // 删除最后一个逗号
            createSql.deleteCharAt(createSql.length() - 2);
        }
        createSql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='" + tableComment + "';");

        return createSql.toString();
    }

    /**
     * 删表sql
     *
     * @param clazz
     */
    public static String generateSqlForDrop(Class<?> clazz) {
        TableName tableNameAnnotation = clazz.getAnnotation(TableName.class);
        String tableName = tableNameAnnotation.value().isEmpty() ? clazz.getSimpleName() : tableNameAnnotation.value();
        return "DROP TABLE IF EXISTS `" + tableName + "`;";
    }

    /**
     * 这个方法需要你根据实际情况实现，比如扫描特定包下的类
     */
    private static List<Class<?>> findAllClasses() {
        List<Class<?>> classes = new ArrayList<>();
        // 实现逻辑来找到所有带有@TableName注解的类
        return classes;
    }
}
