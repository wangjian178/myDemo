package com.wj.demo.core.system.service;


import com.mybatisflex.core.service.IService;
import com.wj.demo.core.system.entity.SysDictType;

import java.io.Serializable;
import java.util.List;

/**
 * 系统-字典类型 服务层。
 *
 * @author wj
 * @since 1.0.0
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    /**
     * 新增系统-字典类型
     * @param sysDictType 系统-字典类型
     */
    void add(SysDictType sysDictType);

    /**
     * 批量新增系统-字典类型
     * @param sysDictTypeList 系统-字典类型
     */
    void addBatch(List<SysDictType> sysDictTypeList);

    /**
     * 批量删除系统-字典类型
     *
     * @param idList 主键集合
     */
    void removeByIdList(List<Serializable> idList);

    /**
     * 查询系统-字典类型
     *
     * @param dictTypeList 字典类型集合
     * @return 字典类型集合
     */
    List<SysDictType> listByDictTypeList(List<String> dictTypeList);

    /**
     * 查询系统-字典类型
     *
     * @param sysDictType 查询参数
     * @return 系统-字典类型
     */
    List<SysDictType> list(SysDictType sysDictType);
}