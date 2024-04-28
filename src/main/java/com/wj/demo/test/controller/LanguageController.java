package com.wj.demo.test.controller;

import com.wj.demo.baseContext.BaseContextHolder;
import com.wj.demo.i18n.CommonMessageSource;
import com.wj.demo.i18n.entity.Language;
import com.wj.demo.i18n.service.LanguageService;
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
    private LanguageService languageService;

    @GetMapping("getMessage")
    public String getMessage(String code) {
        return commonMessageSource.getMessage(code, null, BaseContextHolder.getBaseContext().getLocale());
    }

    @PostMapping("saveBatch")
    public int getMessage(@RequestBody List<Language> languageList) {
        return languageService.saveOrUpdateBatch(languageList);
    }
}
