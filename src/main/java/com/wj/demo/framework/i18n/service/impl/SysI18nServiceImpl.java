package com.wj.demo.framework.i18n.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.constant.I18nConstant;
import com.wj.demo.framework.common.constant.SymbolicConstant;
import com.wj.demo.framework.common.utils.CollectionUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.exception.exception.BusinessException;
import com.wj.demo.framework.i18n.entity.SysI18nEntity;
import com.wj.demo.framework.i18n.mapper.SysI18nMapper;
import com.wj.demo.framework.i18n.service.SysI18nService;
import com.wj.demo.framework.redis.service.RedisClient;
import jakarta.annotation.Resource;
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
public class SysI18nServiceImpl extends ServiceImpl<SysI18nMapper, SysI18nEntity> implements SysI18nService {

    @Resource
    private RedisClient redisClient;

    /**
     * 查询
     * 对外提供
     *
     * @param code     编码
     * @param language 语言
     * @return 显示内容
     */
    @Override
    public String queryMessage(String code, String language) {

        //1.查询缓存
        String label = redisClient.hashGet(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + language, code);

        //2.查询数据库
        if (label == null) {
            SysI18nEntity lang = queryChain()
                    .eq(SysI18nEntity::getCode, code)
                    .eq(SysI18nEntity::getLanguage, language)
                    .one();
            if (lang != null) {
                label = lang.getLabel();
            }
        }

        return label;
    }

    /**
     * 批量查询
     * 对外提供
     *
     * @param codeList 代码集合
     * @param language 语言
     * @return 显示内容集合
     */
    @Override
    public Map<String, String> queryMessageList(List<String> codeList, String language) {
        Map<String, String> result = new LinkedHashMap<>();

        if (CollectionUtils.isEmpty(codeList) || StringUtils.isEmpty(language)) {
            return result;
        }

        //1.查询缓存
        Map<String, String> messageMap = redisClient.hashMultiGet(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + language, codeList.toArray(new String[0]));
        result.putAll(messageMap);

        //2.查询数据库
        if (messageMap.isEmpty()) {
            List<SysI18nEntity> sysI18nEntityList = queryChain()
                    .in(SysI18nEntity::getCode, codeList)
                    .eq(SysI18nEntity::getLanguage, language)
                    .list();
            for (SysI18nEntity entity : sysI18nEntityList) {
                result.put(entity.getCode(), entity.getLabel());
            }
        }

        return result;
    }

    @Override
    public List<SysI18nEntity> queryByCondition(SysI18nEntity condition) {
        return queryChain()
                .eq(SysI18nEntity::getCode, condition.getCode(), StringUtils.isNotEmpty(condition.getCode()))
                .eq(SysI18nEntity::getLanguage, condition.getLanguage(), StringUtils.isNotEmpty(condition.getLanguage()))
                .like(SysI18nEntity::getLabel, condition.getLabel(), StringUtils.isNotEmpty(condition.getLabel()))
                .orderBy(SysI18nEntity::getCreateTime).asc()
                .list();
    }

    @Override
    public List<SysI18nEntity> queryByLanguageAndCode(List<SysI18nEntity> queryList) {
        List<SysI18nEntity> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(queryList)) {
            return result;
        }
        List<List<SysI18nEntity>> lists = CollectionUtils.split(queryList);
        for (List<SysI18nEntity> list : lists) {
            result.addAll(mapper.queryByLanguageAndCode(list));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdateBatch(List<SysI18nEntity> list) {

        //1.查询已有数据 设置ID
        List<SysI18nEntity> existList = queryByLanguageAndCode(list);
        Map<String, SysI18nEntity> existMap = existList.stream().collect(Collectors.toMap(x -> x.getLanguage() + BaseConstant.UNDERLINE + x.getCode(), x -> x));
        for (SysI18nEntity sysI18nEntity : list) {
            String key = sysI18nEntity.getLanguage() + BaseConstant.UNDERLINE + sysI18nEntity.getCode();
            if (existMap.containsKey(key)) {
                sysI18nEntity.setId(existMap.get(key).getId());
            }
        }

        //2.修改
        List<SysI18nEntity> updateList = list.stream().filter(x -> x.getId() != null).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(updateList)) {
            //3.1.删除缓存
            for (SysI18nEntity entity : updateList) {
                redisClient.hashRemove(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + entity.getLanguage(), entity.getCode());
            }

            //3.2修改数据库
            updateBatch(updateList);
        }

        //3.新增
        List<SysI18nEntity> saveList = list.stream().filter(x -> x.getId() == null).collect(Collectors.toList());
        saveBatch(saveList);

        return list.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrModify(SysI18nEntity sysI18nEntity) {
        return saveOrUpdateBatch(Stream.of(sysI18nEntity).collect(Collectors.toList()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id) {
        //1.查询
        SysI18nEntity entity = getById(id);
        if (entity == null) {
            throw new BusinessException("数据不存在");
        }

        //2.删除缓存
        redisClient.hashRemove(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + entity.getLanguage(), entity.getCode());

        //3.删除数据库
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(List<Long> ids) {

        //1.查询
        List<SysI18nEntity> existList = listByIds(ids);
        if (existList.isEmpty()) {
            throw new BusinessException("数据不存在");
        }

        //2.删除缓存
        for (SysI18nEntity entity : existList) {
            redisClient.hashRemove(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + entity.getLanguage(), entity.getCode());
        }

        //3.删除数据库
        return super.removeByIds(ids);
    }
}




