package com.wj.demo.mybatis.generator;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import java.util.Random;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/30 10:19
 */
public class IdGenerator implements IdentifierGenerator {

    /**
     * 配合ASSIGN_ID使用 todo Oracle可以使用自增序列，Mysql可以使用表模拟序列，雪花算法
     * @param entity
     * @return
     */
    @Override
    public Number nextId(Object entity) {

        return new Random().nextInt();
    }
}
