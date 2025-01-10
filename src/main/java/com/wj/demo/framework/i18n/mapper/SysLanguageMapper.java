package com.wj.demo.framework.i18n.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wj.demo.framework.i18n.entity.SysLanguageEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wj
 * @description 针对表【t_language】的数据库操作Mapper
 * @createDate 2024-04-17 15:29:04
 * @Entity generator.entity.Language
 */
public interface SysLanguageMapper extends BaseMapper<SysLanguageEntity> {

    /**
     * language和code查询
     * @param queryList
     * @return
     */
    List<SysLanguageEntity> queryByLanguageAndCode(@Param("queryList") List<SysLanguageEntity> queryList);
}




