package com.wj.demo.core.test.service.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.core.test.entity.EduStudyRecord;
import com.wj.demo.core.test.mapper.EduStudyRecordMapper;
import com.wj.demo.core.test.service.IEduStudyRecordService;
import com.wj.demo.framework.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author W.Jian
 * @description 针对表【edu_study_record】的数据库操作Service实现
 * @createDate 2025-02-24 14:10:17
 */
@Service
public class IEduStudyRecordServiceImpl extends ServiceImpl<EduStudyRecordMapper, EduStudyRecord> implements IEduStudyRecordService {

    @Override
    public List<EduStudyRecord> queryByCondition(EduStudyRecord record) {
        return queryChain()
                .eq(EduStudyRecord::getStudyDate, record.getStudyDate(), record.getStudyDate() != null)
                .eq(EduStudyRecord::getCode, record.getCode(), StringUtils.isNotEmpty(record.getCode()))
                .eq(EduStudyRecord::getDeleted, Boolean.FALSE)
                .list();
    }

    @Override
    public EduStudyRecord saveOrUpdateUnique(EduStudyRecord record) {
        //查询已有数据
        EduStudyRecord exist = queryChain()
                .eq(EduStudyRecord::getStudyDate, record.getStudyDate(), record.getStudyDate() != null)
                .eq(EduStudyRecord::getCode, record.getCode(), StringUtils.isNotEmpty(record.getCode()))
                .eq(EduStudyRecord::getDeleted, Boolean.FALSE)
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




