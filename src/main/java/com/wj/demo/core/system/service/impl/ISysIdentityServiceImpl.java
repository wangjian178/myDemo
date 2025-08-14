package com.wj.demo.core.system.service.impl;


import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wj.demo.core.system.entity.SysIdentity;
import com.wj.demo.core.system.mapper.SysIdentityMapper;
import com.wj.demo.core.system.service.ISysIdentityService;
import com.wj.demo.framework.exception.exception.BusinessException;
import com.wj.demo.framework.redis.service.RedisClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author W.Jian
 * @description 针对表【sys_identity(流水号生成)】的数据库操作Service实现
 * @createDate 2024-09-26 11:45:35
 */
@Service
public class ISysIdentityServiceImpl extends ServiceImpl<SysIdentityMapper, SysIdentity> implements ISysIdentityService {

    @Resource
    private RedisClient redisClient;

    /**
     * 通过编号查询
     *
     * @param code 编号
     * @return SysIdentity
     */
    @Override
    public SysIdentity getSysIdentityByCode(String code) {
        return queryChain()
                .eq(SysIdentity::getDeleted, Boolean.FALSE)
                .eq(SysIdentity::getCode, code)
                .one();
    }


    /**
     * 获取下一个流水号
     *
     * @param code 编号
     * @return 编号
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String getNextNo(String code) {
        String no = null;

        //1.查询编号
        SysIdentity sysIdentity = redisClient.get(code);
        if (sysIdentity == null) {
            sysIdentity = getSysIdentityByCode(code);
        }
        if (sysIdentity == null) {
            return no;
        }

        //2.获取下一个流水号
        sysIdentity.setCurrentValue(sysIdentity.getCurrentValue() + sysIdentity.getStep());

        //3.生成编号
        no = generateNextNo(sysIdentity);

        //4.更新数据库
        updateById(sysIdentity);

        //5.更新缓存
        redisClient.set(code, sysIdentity);

        return no;
    }


    /**
     * {yyyy}表示年份，如2015年表示为2015。
     * {yy}表示年份，如2015年表示为15。
     * {MM}表示月份，如果月份小于10，则加零补齐，如1月份表示为01。
     * {M}表示月份，月份不补齐，如1月份表示为1。
     * {dd}表示日，如果小于10号，则加零补齐，如1号表示为01。
     * {d}表示日，日期不补齐，如1号表示为1。
     * {NO}表示流水号，前面补零。
     * {no}表示流水号，不补零。  基于这个规则用java生成一串序号
     *
     * @param identity 流水号信息
     * @return
     */
    public String generateNextNo(SysIdentity identity) {
        LocalDate localDate = LocalDate.now();
        String yyyy = localDate.format(DateTimeFormatter.ofPattern("yyyy"));
        String yy = localDate.format(DateTimeFormatter.ofPattern("yy"));
        String mm = localDate.format(DateTimeFormatter.ofPattern("MM"));
        String m = localDate.format(DateTimeFormatter.ofPattern("M"));
        String dd = localDate.format(DateTimeFormatter.ofPattern("dd"));
        String d = localDate.format(DateTimeFormatter.ofPattern("d"));
        return identity.getRuler()
                .replace("{yyyy}", yyyy)
                .replace("{yy}", yy)
                .replace("{MM}", mm)
                .replace("{M}", m)
                .replace("{dd}", dd)
                .replace("{d}", d)
                .replace("{NO}", String.format("%0" + identity.getNoLength() + "d", identity.getCurrentValue()))
                .replace("{no}", identity.getCurrentValue().toString());
    }

    /**
     * 保存或修改
     *
     * @param identity 实体
     */
    @Override
    public Boolean saveOrUpdateEntity(SysIdentity identity) {

        if (existWithCode(identity.getCode())) {
            throw new BusinessException("500", "编码已存在！");
        }
        if (identity.getInitValue() == null) {
            identity.setInitValue(0);
        }
        if (identity.getCurrentValue() == null) {
            identity.setCurrentValue(0);
        }
        if (identity.getNoLength() == null) {
            identity.setNoLength(1);
        }
        return saveOrUpdate(identity);
    }

    /**
     * 判断编码是否存在
     *
     * @param code
     * @return
     */
    public boolean existWithCode(String code) {
        return queryChain()
                .eq(SysIdentity::getDeleted, Boolean.FALSE)
                .eq(SysIdentity::getCode, code)
                .exists();
    }
}




