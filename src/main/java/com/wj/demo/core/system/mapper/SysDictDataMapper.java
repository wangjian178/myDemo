package com.wj.demo.core.system.mapper;

import com.wj.demo.core.system.entity.SysDictData;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统-字典项 映射层。
 *
 * @author wj
 * @since 1.0.0
 */
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 根据字典类型和字典值查询字典项
     * @param dictDataList 字典项集合
     * @return 字典项集合
     */
    List<SysDictData> selectByTypeAndValue(List<SysDictData> dictDataList);
}
