package com.wj.demo.i18n.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.demo.i18n.entity.Language;
import com.wj.demo.i18n.mapper.LanguageMapper;
import com.wj.demo.i18n.service.LanguageService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wj
 * @description 针对表【t_language】的数据库操作Service实现
 * @createDate 2024-04-17 15:29:04
 */
@Service
public class LanguageServiceImpl extends ServiceImpl<LanguageMapper, Language> implements LanguageService {

    /**
     * todo 后面加缓存
     *
     * @param code
     * @param language
     * @return
     */
    @Override
    public String queryMessage(String code, String language) {

        String label = code;
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(language)) {
            return label;
        }

        LambdaQueryWrapper<Language> wrapper = new QueryWrapper<Language>()
                .lambda()
                .eq(Language::getCode, code)
                .eq(Language::getLanguage, language);
        Language lang = baseMapper.selectOne(wrapper);
        if (lang != null) {
            label = lang.getLabel();
        }

        return label;
    }

    /**
     * todo 后面加缓存
     *
     * @param codeList
     * @param language
     * @return
     */
    @Override
    public Map<String, String> queryMessageList(List<String> codeList, String language) {
        Map<String, String> result = new LinkedHashMap<>();

        if (CollectionUtils.isEmpty(codeList) || StringUtils.isEmpty(language)) {
            return result;
        }

        LambdaQueryWrapper<Language> wrapper = new QueryWrapper<Language>()
                .lambda()
                .in(Language::getCode, codeList)
                .eq(Language::getLanguage, language);
        List<Language> languageList = baseMapper.selectList(wrapper);
        Map<String, String> codeAndLanguageMap = languageList.stream().collect(Collectors.toMap(Language::getCode, Language::getLabel));
        for (String code : codeList) {
            result.put(code, codeAndLanguageMap.get(code));
        }

        return result;
    }
}




