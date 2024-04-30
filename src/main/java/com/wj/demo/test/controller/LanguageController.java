package com.wj.demo.test.controller;

import com.wj.demo.baseContext.BaseContextHolder;
import com.wj.demo.i18n.CommonMessageSource;
import com.wj.demo.i18n.entity.LanguageMessageEntity;
import com.wj.demo.i18n.service.LanguageMessageService;
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
    private LanguageMessageService languageMessageService;

    @GetMapping("getMessage")
    public String getMessage(String code) {
        return commonMessageSource.getMessage(code, null, BaseContextHolder.getBaseContext().getLocale());
    }

    @PostMapping("saveBatch")
    public int getMessage(@RequestBody List<LanguageMessageEntity> languageMessageEntityList) {
        return languageMessageService.saveOrUpdateBatch(languageMessageEntityList);
    }
}
