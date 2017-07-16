package com.ifox.platform.utility.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT操作工具类
 *
 * @author Yeager
 */
public class JWTUtil {

    /**
     * 生成JWT
     * @param header 头
     * @param payload 载荷
     * @param secret 密码
     * @return JWT字符串
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    public static String generateJWT(JWTHeader header, JWTPayload payload, String secret) throws UnsupportedEncodingException {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put(PublicClaims.ALGORITHM, header.getAlg());
        headerMap.put(PublicClaims.TYPE, header.getTyp());

        return JWT.create()
                .withHeader(headerMap)
                .withIssuer(payload.getIss())
                .withIssuedAt(payload.getIat())
                .withExpiresAt(payload.getExp())
                .withNotBefore(payload.getNbf())
                .withJWTId(payload.getJti())
                .withAudience(payload.getAud())
                .withSubject(payload.getSub())
                .withClaim("userId", payload.getUserId())
                .withClaim("loginName", payload.getLoginName())
                .withClaim("loginMobile", payload.getLoginMobile())
                .withArrayClaim("roleList", payload.getRoleList())
                .sign(algorithm);
    }

    /**
     * 校验JWT
     * @param token JWT
     * @param secret 密钥
     * @return DecodedJWT
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    public static DecodedJWT verifyToken(String token, String secret) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token);
    }

}
