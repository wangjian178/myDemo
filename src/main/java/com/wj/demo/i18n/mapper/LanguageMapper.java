package com.wj.demo.i18n.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wj.demo.i18n.entity.Language;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wj
 * @description 针对表【t_language】的数据库操作Mapper
 * @createDate 2024-04-17 15:29:04
 * @Entity generator.entity.Language
 */
public interface LanguageMapper extends BaseMapper<Language> {

    /**
     * language和code查询
     * @param queryList
     * @return
     */
    List<Language> queryByLanguageAndCode(@Param("queryList") List<Language> queryList);
}




