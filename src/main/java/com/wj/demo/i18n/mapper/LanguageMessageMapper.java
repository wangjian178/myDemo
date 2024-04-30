package com.wj.demo.i18n.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wj.demo.i18n.entity.LanguageMessageEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wj
 * @description 针对表【t_language】的数据库操作Mapper
 * @createDate 2024-04-17 15:29:04
 * @Entity generator.entity.Language
 */
public interface LanguageMessageMapper extends BaseMapper<LanguageMessageEntity> {

    /**
     * language和code查询
     * @param queryList
     * @return
     */
    List<LanguageMessageEntity> queryByLanguageAndCode(@Param("queryList") List<LanguageMessageEntity> queryList);
}




