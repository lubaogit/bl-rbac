package com.bl.rbac.api.kit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bl.rbac.common.vo.OAuth2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenKit {

    private static final Logger log = LoggerFactory.getLogger(TokenKit.class);

    /**
     * 获取token
     */
    public static String getToken(User user) {
        //加密令牌
        String secret = "qwerty";
        //token有效期1小时
        Long expires = 3600L;
        Map<String, Object> map = new HashMap<String, Object>();
        //类型
        map.put("typ", "JWT");
        //加密方式
        map.put("alg", "HS256");
        Long signTime = user.getSignTime();
        String token = JWT.create().withHeader(map)
                .withIssuer("BL-RBAC-API")
                .withClaim("user_id", user.getUserId())
                .withClaim("sign_time", signTime)
                .withClaim("expires", expires)
                .withIssuedAt(new Date(signTime))
                .withExpiresAt(new Date(signTime + expires * 1000))
                .sign(Algorithm.HMAC256(secret));
        log.info("用户user_id ={}，的token{}", user.getUserId(), token);

        OAuth2 o = new OAuth2();
        o.setAccess_token(token);
        o.setAccess_token_in_time(expires);
        //对象中的map还可以负载用户的其他信息
        return token;
    }

    /**
     * 通过token 获取userId
     */
    public  Long getUserId(String token) {
        Map<String, Claim> map = getClaims(token);
        Long userId = map.get("user_id").asLong();
        return userId;
    }

    /**
     * 获取JWT负载信息
     */
    private Map<String, Claim> getClaims(String token) {
        String secret = "qwerty";
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer("BL-RBAC-API")
                //避免token续签导致token过期校验失败
                .withClaim(PublicClaims.EXPIRES_AT, System.currentTimeMillis() / 1000)
                .build();
        DecodedJWT jwt = null;

        try {
            jwt = jwtVerifier.verify(token);
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        } catch (TokenExpiredException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Claim> map = jwt.getClaims();
        return map;
    }


}
