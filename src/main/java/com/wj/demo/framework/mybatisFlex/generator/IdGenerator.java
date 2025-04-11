package com.wj.demo.framework.mybatisFlex.generator;

import com.mybatisflex.core.keygen.IKeyGenerator;

import java.util.UUID;

/**
 * @ClassName KeyGenerator
 * @Description: mybatisFlex自定义主键生成器
 * 通过 @Id(keyType=KeyType.Generator, value=KeyGenerators.flexId)指定
 * @Author: W.Jian
 * @CreateDate: 2025/4/11 14:03
 * @Version:
 */
public class IdGenerator implements IKeyGenerator {
    @Override
    public Object generate(Object entity, String keyColumn) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
