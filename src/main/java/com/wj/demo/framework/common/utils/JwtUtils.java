package com.wj.demo.framework.common.utils;


import com.wj.demo.framework.exception.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 * @Desc 密钥解析
 * @date 2024/7/8 17:37
 */
public class JwtUtils {

    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 创建token
     *
     * @param claims        负载信息
     * @param expireSeconds 过期时间s
     * @param secretKey     密钥
     */
    public static String createToken(Map<String, String> claims, Long expireSeconds, String secretKey) {
        //指定加密算法
        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;
        //密钥
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = DateUtils.addSeconds(now, expireSeconds.intValue());

        return Jwts.builder()
                .issuedAt(now)
                .expiration(expireTime)
                .signWith(key, algorithm)
                .claims(claims)
                .compact();
    }

    /**
     * 解析token
     *
     * @param token     token
     * @param secretKey 密钥
     * @return 解析结果
     */
    public static Jws<Claims> parseJwt(String token, String secretKey) {
        //密钥实例
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.parser()
                //设置签名的密钥
                .verifyWith(key)
                .build()
                //设置要解析的jwt
                .parseSignedClaims(token);
    }

    /**
     * 解析token
     *
     * @param token     token
     * @param secretKey 密钥
     * @return 解析结果
     */
    public static Claims parseToken(String token, String secretKey) {
        try {
            return parseJwt(token, secretKey).getPayload();
        } catch (Exception e) {
            log.error("token解析失败", e);
            throw new BusinessException("token解析失败");
        }
    }
}
