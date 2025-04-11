package com.wj.demo.framework.mybatisFlex.listener;

import com.mybatisflex.annotation.UpdateListener;
import com.wj.demo.framework.common.model.User;
import com.wj.demo.framework.common.utils.SecurityUtils;
import com.wj.demo.framework.mybatisFlex.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * @ClassName EntityUpdateListener
 * @Description: entity更新监听
 * @Author: W.Jian
 * @CreateDate: 2025/4/11 13:57
 * @Version:
 */
public class MybatisUpdateListener implements UpdateListener {
    @Override
    public void onUpdate(Object o) {

        if (o instanceof BaseEntity entity) {
            //更新时间
            entity.setUpdateTime(LocalDateTime.now());

            //获取当前用户
            User user = SecurityUtils.getUser();
            if (user != null) {
                entity.setUpdateBy(user.getUsername());
            }
        }
    }
}
