package com.wj.demo.core.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.demo.core.test.entity.EduStudyRecord;
import com.wj.demo.core.test.mapper.EduStudyRecordMapper;
import com.wj.demo.core.test.service.EduStudyRecordService;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.mybatis.entity.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author W.Jian
 * @description 针对表【edu_study_record】的数据库操作Service实现
 * @createDate 2025-02-24 14:10:17
 */
@Service
public class EduStudyRecordServiceImpl extends ServiceImpl<EduStudyRecordMapper, EduStudyRecord> implements EduStudyRecordService {

    @Override
    public List<EduStudyRecord> queryByCondition(EduStudyRecord record) {
        return lambdaQuery()
                .eq(record.getStudyDate() != null, EduStudyRecord::getStudyDate, record.getStudyDate())
                .eq(StringUtils.isNotEmpty(record.getCode()), EduStudyRecord::getCode, record.getCode())
                .eq(BaseEntity::getDeleted, Boolean.FALSE)
                .list();
    }

    @Override
    public EduStudyRecord saveOrUpdateUnique(EduStudyRecord record) {
        //查询已有数据
        EduStudyRecord exist = lambdaQuery()
                .eq(record.getStudyDate() != null, EduStudyRecord::getStudyDate, record.getStudyDate())
                .eq(StringUtils.isNotEmpty(record.getCode()), EduStudyRecord::getCode, record.getCode())
                .eq(BaseEntity::getDeleted, Boolean.FALSE)
                .one();

        //修改
        if (exist != null) {
            record.setId(exist.getId());
            updateById(record);
            return record;
        }

        //新增
        save(record);
        return record;
    }
}




