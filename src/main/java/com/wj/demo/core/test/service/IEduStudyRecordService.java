package com.wj.demo.core.test.service;

import com.mybatisflex.core.service.IService;
import com.wj.demo.core.test.entity.EduStudyRecord;

import java.util.List;

/**
* @author W.Jian
* @Description 针对表【edu_study_record】的数据库操作Service
* @createDate 2025-02-24 14:10:17
*/
public interface IEduStudyRecordService extends IService<EduStudyRecord> {

    List<EduStudyRecord> queryByCondition(EduStudyRecord record);

    EduStudyRecord saveOrUpdateUnique(EduStudyRecord record);
}
