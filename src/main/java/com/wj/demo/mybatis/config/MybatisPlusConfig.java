package com.wj.demo.mybatis.config;

import com.wj.demo.mybatis.generator.IdGenerator;
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
@MapperScan("com.wj.demo.*.mapper")
public class MybatisPlusConfig {

    @Bean
    public IdGenerator idGenerator(){
        return new IdGenerator();
    }
}
