package com.wj.demo.framework.mybatisFlex.listener;

import com.mybatisflex.annotation.InsertListener;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.common.utils.SecurityUtils;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * @ClassName EntityInsertListener
 * @Description: 插入监听
 * @Author: W.Jian
 * @CreateDate: 2025/4/11 13:52
 * @Version:
 */
public class MybatisInsertListener implements InsertListener {
    @Override
    public void onInsert(Object o) {

        if (o instanceof BaseEntity entity) {
            //设置删除标志
            //entity.setDeleted(Boolean.FALSE);

            //设置创建时间
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());

            //获取当前用户
            LoginUser loginUser = SecurityUtils.getUser();
            if (loginUser != null) {
                entity.setCreateBy(loginUser.getUsername());
                entity.setUpdateBy(loginUser.getUsername());
            }
        }
    }
}
