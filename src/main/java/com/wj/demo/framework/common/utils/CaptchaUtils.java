package com.wj.demo.framework.common.utils;

import com.google.code.kaptcha.Producer;
import com.wj.demo.core.system.model.vo.CaptchaVO;
import com.wj.demo.framework.common.constant.LoginConstant;
import com.wj.demo.framework.common.property.SystemProperties;
import com.wj.demo.framework.exception.enums.ResultCodeEnum;
import com.wj.demo.framework.exception.exception.BusinessException;
import com.wj.demo.framework.redis.service.RedisClient;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CaptchaUtils
 * @Description: 验证码工具类
 * @Author: W.Jian
 * @CreateDate: 2025/7/18 10:54
 * @Version:
 */
@Slf4j
public class CaptchaUtils {

    private final static String PREFIX = "data:image/jpeg;base64";

    /**
     * 创建验证码
     */
    public static CaptchaVO create() {
        Producer producer = SpringContextUtils.getBean(Producer.class);
        //获取验证码
        String code = producer.createText();
        //验证码生成图片
        BufferedImage image = producer.createImage(code);
        //转换为base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (Exception e) {
            log.info("验证码生成图片异常", e);
            throw new BusinessException(ResultCodeEnum.CAPTCHA_CREATE_ERROR);
        }
        String base64Img = PREFIX + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        //生成验证码id
        String id = String.valueOf(System.currentTimeMillis());
        //获取默认过期时间
        long expireTime = SpringContextUtils.getBean(SystemProperties.class).getSecurity().getCaptchaExpireTime();
        //缓存验证码
        SpringContextUtils.getBean(RedisClient.class).set(LoginConstant.LOGIN_CAPTCHA_KEY + id, code, expireTime, TimeUnit.SECONDS);
        return new CaptchaVO().setCaptchaId(id).setBase64Img(base64Img).setExpireTime(expireTime);
    }

    /**
     * 校验验证码
     */
    public static void checkCaptcha(String id, String code) {
        //获取缓存验证码
        String cacheCode = SpringContextUtils.getBean(RedisClient.class).get(LoginConstant.LOGIN_CAPTCHA_KEY + id);
        if (StringUtils.isEmpty(cacheCode)) {
            throw new BusinessException(ResultCodeEnum.CAPTCHA_EXPIRE_ERROR);
        }
        if (!code.equals(cacheCode)) {
            throw new BusinessException(ResultCodeEnum.CAPTCHA_ERROR);
        }
    }
}
