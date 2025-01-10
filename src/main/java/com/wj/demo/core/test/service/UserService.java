package com.wj.demo.core.test.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.demo.framework.common.model.User;
import com.wj.demo.core.test.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/5/6 16:01
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    public void test(){
        // todo 研究一下lambda表达式方法怎么写
        LambdaQueryWrapper<User> eq = new QueryWrapper<User>().lambda().eq(User::getUsername, "");
    }
}
