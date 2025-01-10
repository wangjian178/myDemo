package com.wj.demo.framework.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 * @Desc 密钥解析
 * @date 2024/7/8 17:37
 */
public class JwtUtils {

    /**
     * 自定义密钥
     */
    private final static String SIGN = "123";

    /**
     * 算法
     */
    private final static Algorithm ALGORITHM = Algorithm.HMAC256(SIGN);

    /**
     * 过期时间 ms 默认一天
     */
    private final static Integer EXPIRE = 24 * 60 * 60 * 1000;


    /**
     * 创建token
     *
     * @param map 用户信息
     */
    public static String createToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();

        //设置payload
        map.forEach(builder::withClaim);

        //过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, EXPIRE);
        builder.withExpiresAt(calendar.getTime());

        //签名 自定义密钥
        return builder.sign(ALGORITHM);
    }


    /**
     * 验证 Token 合法性
     *
     * @param token 校验信息
     */
    public static boolean checkToken(String token) {
        boolean verify = true;

        try {
            getTokenInfo(token);
        } catch (Exception e) {
            verify = false;
        }

        return verify;
    }


    /**
     * 获取 Token 信息
     *
     * @param token 校验信息
     */
    public static DecodedJWT getTokenInfo(String token) {
        return JWT.require(ALGORITHM).build().verify(token);
    }


}
