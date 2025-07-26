package com.wj.demo.core.system.service;


import com.mybatisflex.core.service.IService;
import com.wj.demo.core.system.entity.SysDictData;
import com.wj.demo.framework.common.model.Node;

import java.util.List;

/**
 * 系统-字典项 服务层。
 *
 * @author wj
 * @since 1.0.0
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 添加系统-字典项
     * @param sysDictData 系统-字典项
     * @return 添加结果
     */
    void add(SysDictData sysDictData);

    /**
     * 添加系统-字典项
     *
     * @param sysDictDataList 系统-字典项
     */
    void addBatch(List<SysDictData> sysDictDataList);

    /**
     * 查询所有系统-字典项
     *
     * @param dictTypeList 字典类型
     */
    void removeByDictTypeList(List<String> dictTypeList);

    /**
     * 根据字典类型和字典值查询字典项
     * @param dictDataList 字典项集合
     * @return 字典项集合
     */
    List<SysDictData> selectByTypeAndValue(List<SysDictData> dictDataList);

    /**
     * 查询所有系统-字典项
     *
     * @param dictType 字典类型
     * @return 系统-字典项列表
     */
    List<SysDictData> listByDictType(String dictType);

    /**
     * 查询所有系统-字典项
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 系统-字典项列表
     */
    SysDictData listByDictType(String dictType, String dictValue);

    /**
     * 查询所有系统-字典项
     *
     * @param sysDictData 查询参数
     * @return 系统-字典项列表
     */
    List<SysDictData> list(SysDictData sysDictData);

    /**
     * 查询所有系统-字典项并转换为树形结构
     *
     * @param dictType 字典类型
     * @return 系统-字典项树形结构列表
     */
    List<Node<SysDictData>> listAsTree(String dictType);
}