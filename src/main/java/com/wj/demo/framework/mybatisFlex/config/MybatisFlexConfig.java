package com.wj.demo.framework.mybatisFlex.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.keygen.KeyGeneratorFactory;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;
import com.wj.demo.framework.mybatisFlex.generator.IdGenerator;
import com.wj.demo.framework.mybatisFlex.listener.MybatisInsertListener;
import com.wj.demo.framework.mybatisFlex.listener.MybatisUpdateListener;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisFlexConfig
 * @Description: myabtisFlex配置
 * @Author: W.Jian
 * @CreateDate: 2025/4/11 13:50
 * @Version:
 */
@Configuration
public class MybatisFlexConfig {

    private static final Logger logger = LoggerFactory.getLogger("mybatis-flex-sql");

    public MybatisFlexConfig() {

        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();
        //注册插入监听器
        config.registerInsertListener(new MybatisInsertListener(), BaseEntity.class);
        //注册更新监听器
        config.registerUpdateListener(new MybatisUpdateListener(), BaseEntity.class);

        //开启SQL审计功能
        AuditManager.setAuditEnable(true);
        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(
                auditMessage -> logger.debug("{},{}ms", auditMessage.getFullSql(), auditMessage.getElapsedTime())
        );

        //注册主键生成器
        KeyGeneratorFactory.register("IdGenerator", new IdGenerator());
    }
}
