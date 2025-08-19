package com.wj.demo.framework.i18n.service;


import com.mybatisflex.core.service.IService;
import com.wj.demo.framework.i18n.entity.SysI18nEntity;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @description 针对表【t_language】的数据库操作Service
 * @createDate 2024-04-17 15:29:04
 */
public interface SysI18nService extends IService<SysI18nEntity> {

    /**
     * 单个查询
     * 对外提供
     * @param code
     * @param language
     * @return
     */
    String queryMessage(String code, String language);

    /**
     * 批量查询
     * 对外提供
     * @param codeList
     * @param language
     * @return
     */
    Map<String, String> queryMessageList(List<String> codeList, String language);

    /**
     * 条件查询
     *
     * @param
     * @param condition
     * @return
     */
    List<SysI18nEntity> queryByCondition(SysI18nEntity condition);

    /**
     * 通过language和code查询
     *
     * @param
     * @param queryList
     * @return
     */
    List<SysI18nEntity> queryByLanguageAndCode(List<SysI18nEntity> queryList);

    /**
     * 批量新增或保存 覆盖
     * @param list
     * @return
     */
    int saveOrUpdateBatch(List<SysI18nEntity> list);

    /**
     * 新增或保存
     * @param sysI18nEntity
     * @return
     */
    int saveOrModify(SysI18nEntity sysI18nEntity);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean removeById(Long id);

    /**
     * 删除
     * @param ids
     * @return
     */
    boolean removeByIds(List<Long> ids);
}
