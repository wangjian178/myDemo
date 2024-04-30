package com.wj.demo.test.controller;

import com.wj.demo.common.constant.BaseConstant;
import com.wj.demo.common.model.vo.UserVO;
import com.wj.demo.exception.model.Result;
import com.wj.demo.redis.service.RedisClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/30 17:36
 */
@Controller
@RequestMapping("sys")
public class LoginController {

    @Resource
    private RedisClient redisClient;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @GetMapping("login")
    @ResponseBody
    public Result login(UserVO user) {

        //缓存的KEY
        String timesKey = BaseConstant.LOCK_USER_RETRY_TIMES_PREFIX + user.getUsername();

        //  1.获取错误次数
        Integer times = 0;
        Integer cacheTimes = redisClient.get(timesKey);
        if (cacheTimes != null) {
            times = cacheTimes;
        }

        //  2.已锁定提示报错
        if (times.equals(BaseConstant.LOCK_USER_MAX_RETRY_TIMES)) {
            long restLockSeconds = redisClient.getExpire(timesKey);
            return Result.ofFail("用户已锁定！" + "请在" + new BigDecimal(restLockSeconds).divide(new BigDecimal(60), 0, RoundingMode.HALF_UP) + "分钟后重试！");
        }

        //  3.查询用户
        UserVO existUser = null;//new UserVO().setUsername(user.getUsername()).setPassword(user.getPassword());
        if (Objects.isNull(existUser)) {
            //剩余次数
            Integer restTimes = (times.equals(0) ? BaseConstant.LOCK_USER_MAX_RETRY_TIMES : BaseConstant.LOCK_USER_MAX_RETRY_TIMES - times) - 1;
            //锁定次数加1,并且重置过期时间
            redisClient.set(timesKey, times + 1, BaseConstant.LOCK_USER_LOCK_SECONDS);
            //返回错误信息
            return Result.ofFail((restTimes.equals(0) ? "已超过最大尝试次数，账户已锁定！" : "用户名或密码不正确！") + "剩余可重试次数" + restTimes + "次");
        }

        // 4.校验密码 密码加密

        // 5.生成token 并且记录

        // 6.修改登陆状态

        // 7.登陆成功清除失败次数
        redisClient.delete(timesKey);

        return Result.ofSuccess();
    }


    /**
     * 用户注销
     *
     * @param response
     * @param request
     * @return
     */
    @GetMapping(value = "/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request) {

        //  1.修改用户状态

        //  2.清理cookie

        //  3.清理session
        HttpSession session = request.getSession();
        session.invalidate();

        //  4.跳转到登陆页面
        return "redirect:/sys/login";
    }
}
