package com.wj.demo.i18n.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wj.demo.i18n.entity.Language;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @description 针对表【t_language】的数据库操作Service
 * @createDate 2024-04-17 15:29:04
 */
public interface LanguageService extends IService<Language> {

    /**
     * 单个查询
     *
     * @param code
     * @param language
     * @return
     */
    String queryMessage(String code, String language);

    /**
     * 批量查询
     *
     * @param codeList
     * @param language
     * @return
     */
    Map<String, String> queryMessageList(List<String> codeList, String language);
}
