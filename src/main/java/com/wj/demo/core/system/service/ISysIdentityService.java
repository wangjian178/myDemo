package com.wj.demo.core.system.service;


import com.mybatisflex.core.service.IService;
import com.wj.demo.core.system.entity.SysIdentity;

/**
 * @author W.Jian
 * @description 针对表【sys_identity(流水号生成)】的数据库操作Service
 * @createDate 2024-09-26 11:45:35
 */
public interface ISysIdentityService extends IService<SysIdentity> {

    /**
     * 通过编号查询
     *
     * @param code 编号
     * @return SysIdentity
     */
    SysIdentity getSysIdentityByCode(String code);

    /**
     * 获取下一个流水号
     *
     * @param code 编号
     * @return 编号
     */
    String getNextNo(String code);

    /**
     * 保存或修改
     *
     * @param identity 实体
     */
    Boolean saveOrUpdateEntity(SysIdentity identity);
}
