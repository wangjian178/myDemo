package com.wj.demo.core.system.service.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.core.system.entity.SysDictType;
import com.wj.demo.core.system.mapper.SysDictTypeMapper;
import com.wj.demo.core.system.service.ISysDictDataService;
import com.wj.demo.core.system.service.ISysDictTypeService;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.exception.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统-字典类型 服务层实现。
 *
 * @author wj
 * @since 1.0.0
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Resource
    private ISysDictDataService sysDictDataService;

    /**
     * 校验字典项
     */
    private void checkDictType(List<SysDictType> dictTypeList) {
        List<SysDictType> existList = listByDictTypeList(dictTypeList.stream().map(SysDictType::getType).toList());
        String errorMsg = existList.stream().map(x -> "字典" + x.getType() + "已存在").collect(Collectors.joining("；"));
        if (StringUtils.isNotEmpty(errorMsg)) {
            throw new BaseException(errorMsg);
        }
    }

    /**
     * 新增系统-字典类型
     *
     * @param sysDictType 系统-字典类型
     */
    @Override
    public void add(SysDictType sysDictType) {
        checkDictType(List.of(sysDictType));
        save(sysDictType);
    }

    /**
     * 批量新增系统-字典类型
     *
     * @param sysDictTypeList 系统-字典类型
     */
    @Override
    public void addBatch(List<SysDictType> sysDictTypeList) {
        checkDictType(sysDictTypeList);
        super.saveBatch(sysDictTypeList);
    }

    /**
     * 批量删除系统-字典类型
     *
     * @param idList 主键集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeByIdList(List<Serializable> idList) {
        //1.查询字典数据
        List<SysDictType> dictTypeList = listByIds(idList);
        //2.删除字典数据
        removeByIds(idList);
        //3.删除字典值数据
        sysDictDataService.removeByDictTypeList(dictTypeList.stream().map(SysDictType::getType).toList());
    }

    /**
     * 查询系统-字典类型
     *
     * @param dictTypeList 字典类型集合
     * @return 字典类型集合
     */
    @Override
    public List<SysDictType> listByDictTypeList(List<String> dictTypeList) {
        return queryChain()
                .eq(SysDictType::getDeleted, Boolean.FALSE)
                .eq(SysDictType::getType, dictTypeList)
                .list();
    }

    /**
     * 查询系统-字典类型
     *
     * @param sysDictType 查询参数
     * @return 系统-字典类型
     */
    @Override
    public List<SysDictType> list(SysDictType sysDictType) {
        return queryChain()
                .eq(SysDictType::getDeleted, Boolean.FALSE)
                .like(SysDictType::getName, sysDictType.getName(), StringUtils.isNotEmpty(sysDictType.getName()))
                .like(SysDictType::getType, sysDictType.getType(), StringUtils.isNotEmpty(sysDictType.getType()))
                .eq(SysDictType::getStatus, sysDictType.getStatus(), Objects.nonNull(sysDictType.getStatus()))
                .list();
    }
}