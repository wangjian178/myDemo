package com.wj.demo.framework.i18n.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wj.demo.framework.i18n.entity.SysLanguageEntity;

import java.util.List;
import java.util.Map;

/**
 * @author wj
 * @description 针对表【t_language】的数据库操作Service
 * @createDate 2024-04-17 15:29:04
 */
public interface SysLanguageService extends IService<SysLanguageEntity> {

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
    List<SysLanguageEntity> queryByCondition(SysLanguageEntity condition);

    /**
     * 通过language和code查询
     *
     * @param
     * @param queryList
     * @return
     */
    List<SysLanguageEntity> queryByLanguageAndCode(List<SysLanguageEntity> queryList);

    /**
     * 批量新增或保存 覆盖
     * @param list
     * @return
     */
    int saveOrUpdateBatch(List<SysLanguageEntity> list);

    /**
     * 新增或保存
     * @param sysLanguageEntity
     * @return
     */
    int saveOrModify(SysLanguageEntity sysLanguageEntity);

    /**
     * 删除
     * @param id
     * @return
     */
    int removeById(Long id);

    /**
     * 删除
     * @param ids
     * @return
     */
    int removeByIds(List<Long> ids);
}
