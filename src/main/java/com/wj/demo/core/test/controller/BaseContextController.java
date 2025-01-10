package com.wj.demo.core.test.controller;

import com.wj.demo.framework.baseContext.BaseContextHolder;
import com.wj.demo.framework.baseContext.BaseContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/17 11:31
 */
@RestController
@RequestMapping("baseContext")
public class BaseContextController {

    @GetMapping("get")
    public BaseContext setBaseContext(){
        BaseContext param = BaseContextHolder.getBaseContext();
        return param;
    }

    @GetMapping("get1")
    public BaseContext setBaseContext1(){
        BaseContext param = BaseContextHolder.getBaseContext();
        return param;
    }
}
