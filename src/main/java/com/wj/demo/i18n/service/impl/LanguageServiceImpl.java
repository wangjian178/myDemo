package com.wj.demo.i18n.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.demo.common.constant.BaseConstant;
import com.wj.demo.common.utils.CollectionUtils;
import com.wj.demo.common.utils.StringUtils;
import com.wj.demo.i18n.entity.Language;
import com.wj.demo.i18n.mapper.LanguageMapper;
import com.wj.demo.i18n.service.LanguageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wj
 * @description 针对表【t_language】的数据库操作Service实现
 * @createDate 2024-04-17 15:29:04
 */
@Service
public class LanguageServiceImpl extends ServiceImpl<LanguageMapper, Language> implements LanguageService {

    /**
     * 查询
     * 对外提供
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

        //1.查询缓存 todo


        //2.查询数据库
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
     * 批量查询
     * 对外提供
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

        //1.查询缓存 todo


        //2.查询数据库
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

    @Override
    public List<Language> queryByCondition(Language condition) {
        return baseMapper.selectList(
                new QueryWrapper<Language>()
                        .lambda()
                        .eq(StringUtils.isNotEmpty(condition.getCode()), Language::getCode, condition.getCode())
                        .eq(StringUtils.isNotEmpty(condition.getLanguage()), Language::getLanguage, condition.getLanguage())
                        .like(StringUtils.isNotEmpty(condition.getLabel()), Language::getLabel, condition.getLabel())
        );
    }

    @Override
    public List<Language> queryByLanguageAndCode(List<Language> queryList) {
        List<Language> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(queryList)) {
            return result;
        }
        List<List<Language>> lists = CollectionUtils.split(queryList);
        for (List<Language> list : lists) {
            result.addAll(baseMapper.queryByLanguageAndCode(list));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdateBatch(List<Language> list) {

        //1.查询
        List<Language> existList = queryByLanguageAndCode(list);
        Map<String, Language> existMap = existList.stream().collect(Collectors.toMap(x -> x.getLanguage() + BaseConstant.SLASH + x.getCode(), x -> x));
        for (Language language : list) {
            String key = language.getLanguage() + BaseConstant.SLASH + language.getCode();
            if (existMap.containsKey(key)) {
                language.setId(existMap.get(key).getId());
            }
        }

        //2.新增
        List<Language> saveList = list.stream().filter(x -> x.getId() == null).collect(Collectors.toList());
        saveBatch(saveList);

        //3.修改
        List<Language> updateList = list.stream().filter(x -> x.getId() != null).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(updateList)) {
            //3.1.删除缓存 todo

            //3.2修改数据库
            updateBatchById(updateList);
        }


        return list.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrModify(Language language) {
        return saveOrUpdateBatch(Stream.of(language).collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id) {

        //1.删除缓存 todo

        //2.删除数据库
        int count = baseMapper.deleteById(id);

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(List<Long> ids) {

        //1.删除缓存 todo

        //2.删除数据库
        List<List<Long>> idsList = CollectionUtils.split(ids);
        for (List<Long> idList : idsList) {
            baseMapper.deleteBatchIds(idList);
        }

        return ids.size();
    }
}




