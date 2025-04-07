package com.wj.demo.framework.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.wj.demo.framework.mybatis.generator.IdGenerator;
import com.wj.demo.framework.mybatis.page.interceptor.MybatisPaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/30 10:20
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.wj.demo.*.*.mapper")
public class MybatisPlusConfig {

    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 如果配置多个插件, 切记分页最后添加
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        interceptor.addInnerInterceptor(new MybatisPaginationInterceptor());
        return interceptor;
    }
}
