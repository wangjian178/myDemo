package com.wj.demo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wj.demo.system.entity.SysIdentity;

/**
 * @author W.Jian
 * @description 针对表【sys_identity(流水号生成)】的数据库操作Service
 * @createDate 2024-09-26 11:45:35
 */
public interface SysIdentityService extends IService<SysIdentity> {

    /**
     * 通过编号查询
     *
     * @param code 编号
     * @return SysIdentity
     */
    SysIdentity getSysIdentityByCode(String code);

    String getNextNo(String code);

    Boolean saveOrUpdateEntity(SysIdentity identity);
}
