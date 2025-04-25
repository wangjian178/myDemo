package com.wj.demo.core.system.service.impl.logout;

import com.wj.demo.core.system.config.property.SysConfigProperty;
import com.wj.demo.core.system.enums.UserOnLineStatusEnum;
import com.wj.demo.core.system.model.vo.LogoutResultVO;
import com.wj.demo.core.system.service.ILogoutService;
import com.wj.demo.framework.baseContext.BaseContextHolder;
import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.model.LoginUser;
import com.wj.demo.framework.redis.service.RedisClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

/**
 * @ClassName DefaultLogoutServiceImpl
 * @Description: 默认登出处理
 * @Author: W.Jian
 * @CreateDate: 2025/4/1 14:18
 * @Version:
 */
@Service("ILogoutService" + BaseConstant.UNDERLINE + BaseConstant.DEFAULT)
public class DefaultLogoutServiceImpl implements ILogoutService {

    @Resource
    private SysConfigProperty sysConfigProperty;

    @Resource
    private RedisClient redisClient;

    /**
     * 登出
     *
     * @return token
     */
    @Override
    public LogoutResultVO logout(HttpServletRequest request) {
        LogoutResultVO resultVO = new LogoutResultVO();

        //  1.修改用户状态
        LoginUser loginUser = BaseContextHolder.getBaseContext().getLoginUser();
        loginUser.setOnLineStatus(UserOnLineStatusEnum.OFFLINE);

        //  2.删除token
        redisClient.delete(BaseConstant.TOKEN_PREFIX + BaseContextHolder.getBaseContext().getLoginUser().getId());

        //  3.清理cookie

        //  4.清理session
        HttpSession session = request.getSession();
        session.invalidate();

        //  4.跳转到登陆页面
        resultVO.setRedirectUrl(sysConfigProperty.getLoginUrl());
        return resultVO;
    }
}
