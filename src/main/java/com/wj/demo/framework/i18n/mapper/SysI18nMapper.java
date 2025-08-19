package com.wj.demo.framework.i18n.mapper;

import com.mybatisflex.core.BaseMapper;
import com.wj.demo.framework.i18n.entity.SysI18nEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wj
 * @description 针对表【t_language】的数据库操作Mapper
 * @createDate 2024-04-17 15:29:04
 * @Entity generator.entity.Language
 */
public interface SysI18nMapper extends BaseMapper<SysI18nEntity> {

    /**
     * language和code查询
     * @param queryList
     * @return
     */
    List<SysI18nEntity> queryByLanguageAndCode(@Param("queryList") List<SysI18nEntity> queryList);
}




