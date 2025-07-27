package com.wj.demo.core.file.service.impl;


import com.wj.demo.core.file.service.ISysFileService;
import org.springframework.stereotype.Service;
import com.wj.demo.core.file.entity.SysFile;
import com.wj.demo.core.file.mapper.SysFileMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 文件管理 服务层实现。
 *
 * @author wj
 * @since 1.0.0
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

}