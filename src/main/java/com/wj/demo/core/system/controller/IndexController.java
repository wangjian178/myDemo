package com.wj.demo.core.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName IndexController
 * @Description: 首页
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 15:11
 * @Version:
 */
@Tag(name = "首页功能")
@Controller
public class IndexController {

    @Operation(summary = "首页")
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
