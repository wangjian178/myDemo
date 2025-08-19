package com.wj.demo.core.test.controller;

import com.wj.demo.framework.baseContext.BaseContextHolder;
import com.wj.demo.framework.i18n.CommonMessageSource;
import com.wj.demo.framework.i18n.entity.SysI18nEntity;
import com.wj.demo.framework.i18n.service.SysI18nService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/17 16:08
 */
@RestController
@RequestMapping("lang")
public class LanguageController {

    @Resource
    private CommonMessageSource commonMessageSource;

    @Resource
    private SysI18nService sysI18nService;

    @GetMapping("getMessage")
    public String getMessage(String code) {
        return commonMessageSource.getMessage(code, null, BaseContextHolder.getBaseContext().getLocale());
    }

    @PostMapping("saveBatch")
    public int getMessage(@RequestBody List<SysI18nEntity> sysI18nEntityList) {
        return sysI18nService.saveOrUpdateBatch(sysI18nEntityList);
    }
}
