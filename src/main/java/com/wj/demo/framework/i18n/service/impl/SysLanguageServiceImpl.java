package com.wj.demo.framework.i18n.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.utils.CollectionUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.i18n.entity.SysLanguageEntity;
import com.wj.demo.framework.i18n.mapper.SysLanguageMapper;
import com.wj.demo.framework.i18n.service.SysLanguageService;
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
 * @description 针对表【t_language_message】的数据库操作Service实现
 * @createDate 2024-04-17 15:29:04
 */
@Service
public class SysLanguageServiceImpl extends ServiceImpl<SysLanguageMapper, SysLanguageEntity> implements SysLanguageService {

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
        SysLanguageEntity lang = queryChain()
                .eq(SysLanguageEntity::getCode, code)
                .eq(SysLanguageEntity::getLanguage, language)
                .one();
        if (lang != null) {
            label = lang.getLabel();
        }

        return label;
    }

    /**
     * 批量查询
     * 对外提供
     *
     * @param codeList 代码集合
     * @param language 语言
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
        List<SysLanguageEntity> sysLanguageEntityList = queryChain()
                .in(SysLanguageEntity::getCode, codeList)
                .eq(SysLanguageEntity::getLanguage, language)
                .list();
        Map<String, String> codeAndLanguageMap = sysLanguageEntityList.stream().collect(Collectors.toMap(SysLanguageEntity::getCode, SysLanguageEntity::getLabel));
        for (String code : codeList) {
            result.put(code, codeAndLanguageMap.get(code));
        }

        return result;
    }

    @Override
    public List<SysLanguageEntity> queryByCondition(SysLanguageEntity condition) {
        return queryChain()
                .eq(SysLanguageEntity::getCode, condition.getCode(), StringUtils.isNotEmpty(condition.getCode()))
                .eq(SysLanguageEntity::getLanguage, condition.getLanguage(), StringUtils.isNotEmpty(condition.getLanguage()))
                .like(SysLanguageEntity::getLabel, condition.getLabel(), StringUtils.isNotEmpty(condition.getLabel()))
                .orderBy(SysLanguageEntity::getCreateTime).asc()
                .list();
    }

    @Override
    public List<SysLanguageEntity> queryByLanguageAndCode(List<SysLanguageEntity> queryList) {
        List<SysLanguageEntity> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(queryList)) {
            return result;
        }
        List<List<SysLanguageEntity>> lists = CollectionUtils.split(queryList);
        for (List<SysLanguageEntity> list : lists) {
            result.addAll(mapper.queryByLanguageAndCode(list));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdateBatch(List<SysLanguageEntity> list) {

        //1.查询
        List<SysLanguageEntity> existList = queryByLanguageAndCode(list);
        Map<String, SysLanguageEntity> existMap = existList.stream().collect(Collectors.toMap(x -> x.getLanguage() + BaseConstant.UNDERLINE + x.getCode(), x -> x));
        for (SysLanguageEntity sysLanguageEntity : list) {
            String key = sysLanguageEntity.getLanguage() + BaseConstant.UNDERLINE + sysLanguageEntity.getCode();
            if (existMap.containsKey(key)) {
                sysLanguageEntity.setId(existMap.get(key).getId());
            }
        }

        //2.修改
        List<SysLanguageEntity> updateList = list.stream().filter(x -> x.getId() != null).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(updateList)) {
            //3.1.删除缓存 todo

            //3.2修改数据库
            updateBatch(updateList);
        }

        //3.新增
        List<SysLanguageEntity> saveList = list.stream().filter(x -> x.getId() == null).collect(Collectors.toList());
        saveBatch(saveList);

        return list.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrModify(SysLanguageEntity sysLanguageEntity) {
        return saveOrUpdateBatch(Stream.of(sysLanguageEntity).collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id) {

        //1.删除缓存 todo

        //2.删除数据库

        return mapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(List<Long> ids) {

        //1.删除缓存 todo

        //2.删除数据库
        List<List<Long>> idsList = CollectionUtils.split(ids);
        for (List<Long> idList : idsList) {
            mapper.deleteBatchByIds(idList);
        }

        return ids.size();
    }
}




