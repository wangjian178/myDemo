package com.wj.demo.test.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.demo.common.model.vo.UserVO;
import com.wj.demo.test.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/5/6 16:01
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserVO> {

    public void test(){
        // todo 研究一下lambda表达式方法怎么写
        LambdaQueryWrapper<UserVO> eq = new QueryWrapper<UserVO>().lambda().eq(UserVO::getUsername, "");
    }
}
