package com.wj.demo.core.system.service.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.core.system.entity.SysDictData;
import com.wj.demo.core.system.mapper.SysDictDataMapper;
import com.wj.demo.core.system.service.ISysDictDataService;
import com.wj.demo.framework.common.model.Node;
import com.wj.demo.framework.common.utils.CollectionUtils;
import com.wj.demo.framework.common.utils.NodeUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.exception.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统-字典项 服务层实现。
 *
 * @author wj
 * @since 1.0.0
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {


    /**
     * 校验字典项
     */
    private void checkDictData(List<SysDictData> dictDataList) {
        List<SysDictData> existList = selectByTypeAndValue(dictDataList);
        String errorMsg = existList.stream().map(x -> "字典" + x.getDictType() + "下已存在字典值" + x.getDictValue()).collect(Collectors.joining("；"));
        if (StringUtils.isNotEmpty(errorMsg)) {
            throw new BusinessException(errorMsg);
        }
    }

    /**
     * 添加系统-字典项
     *
     * @param sysDictData 系统-字典项
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysDictData sysDictData) {
        checkDictData(List.of(sysDictData));
        save(sysDictData);
    }


    /**
     * 添加系统-字典项
     *
     * @param sysDictDataList 系统-字典项
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addBatch(List<SysDictData> sysDictDataList) {
        checkDictData(sysDictDataList);
        super.saveBatch(sysDictDataList);
    }

    /**
     * 查询所有系统-字典项
     *
     * @param dictTypeList 字典类型
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeByDictTypeList(List<String> dictTypeList) {
        List<SysDictData> existList = queryChain()
                .eq(SysDictData::getDeleted, Boolean.FALSE)
                .in(SysDictData::getDictType, dictTypeList)
                .list();
        removeByIds(existList.stream().map(SysDictData::getId).toList());
    }

    /**
     * 根据字典类型和字典值查询字典项
     *
     * @param dictDataList 字典项集合
     * @return 字典项集合
     */
    @Override
    public List<SysDictData> selectByTypeAndValue(List<SysDictData> dictDataList) {
        if (CollectionUtils.isEmpty(dictDataList)) {
            return List.of();
        }
        return getMapper().selectByTypeAndValue(dictDataList);
    }

    /**
     * 查询所有系统-字典项
     *
     * @param dictType 字典类型
     * @return 系统-字典项列表
     */
    @Override
    public List<SysDictData> listByDictType(String dictType) {
        return list(new SysDictData().setDictType(dictType));
    }

    /**
     * 查询所有系统-字典项
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 系统-字典项列表
     */
    @Override
    public SysDictData listByDictType(String dictType, String dictValue) {
        return queryChain()
                .eq(SysDictData::getDeleted, Boolean.FALSE)
                .eq(SysDictData::getDictType, dictType, StringUtils.isNotEmpty(dictType))
                .eq(SysDictData::getDictValue, dictValue, StringUtils.isNotEmpty(dictValue))
                .one();
    }

    /**
     * 查询所有系统-字典项
     *
     * @param sysDictData 查询参数
     * @return 系统-字典项列表
     */
    @Override
    public List<SysDictData> list(SysDictData sysDictData) {
        return queryChain()
                .eq(SysDictData::getDeleted, Boolean.FALSE)
                .eq(SysDictData::getDictType, sysDictData.getDictType(), StringUtils.isNotEmpty(sysDictData.getDictType()))
                .like(SysDictData::getDictLabel, sysDictData.getDictLabel(), StringUtils.isNotEmpty(sysDictData.getDictLabel()))
                .eq(SysDictData::getDictValue, sysDictData.getDictValue(), StringUtils.isNotEmpty(sysDictData.getDictValue()))
                .eq(SysDictData::getStatus, sysDictData.getStatus(), Objects.nonNull(sysDictData.getStatus()))
                .orderBy(SysDictData::getSort).asc()
                .list();
    }

    /**
     * 查询所有系统-字典项并转换为树形结构
     *
     * @param dictType 字典类型
     * @return 系统-字典项树形结构列表
     */
    @Override
    public List<Node<SysDictData>> listAsTree(String dictType) {
        List<SysDictData> result = list(new SysDictData().setDictType(dictType));
        List<Node<SysDictData>> nodeList = NodeUtils.tree(result, SysDictData::getDictValue, SysDictData::getParentValue);
        NodeUtils.sortTree(nodeList, SysDictData::getSort, true);
        return nodeList;
    }
}