package com.wj.demo.core.test.controller;

import com.wj.demo.core.test.entity.EduStudyRecord;
import com.wj.demo.core.test.service.IEduStudyRecordService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author W.Jian
 * @version 1.0
 * @Desc 描述
 * @date 2025/1/10 16:44
 */
@RestController
@RequestMapping("/EduStudyRecord")
public class EduStudyRecordController {

    @Resource
    private IEduStudyRecordService IEduStudyRecordService;

    /**
     * 条件查询
     * @param record
     * @return
     */
    @GetMapping(value = "/queryByCondition")
    public List<EduStudyRecord> queryByCondition(EduStudyRecord record) {
        return IEduStudyRecordService.queryByCondition(record);
    }

    @PostMapping(value = "/saveOrUpdateUnique")
    public EduStudyRecord saveOrUpdateUnique(@RequestBody EduStudyRecord record) {
        return IEduStudyRecordService.saveOrUpdateUnique(record);
    }

}
