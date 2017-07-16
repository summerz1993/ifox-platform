package com.ifox.platform.utility.jwt;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.datetime.DateTimeUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * JWTUtil测试类
 * @author Yeager
 */
public class JWTUtilTest {

    private String secret = "123456";

    private String userId = "0983047023742h92h2f";

    /**
     * 测试JWT生成
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    @Test
    public void testGenerateJWT() throws UnsupportedEncodingException {
        JWTHeader header = new JWTHeader();
        JWTPayload payload = new JWTPayload();
        payload.setIss("Yeager");
        payload.setIat(new Date());
        payload.setExp(new Date());
        payload.setNbf(new Date());
        payload.setJti(UUIDUtil.randomUUID());
        payload.setUserId(userId);

        String generateJWT = JWTUtil.generateJWT(header, payload, secret);
        System.out.println("JWT : " + generateJWT);

    }

    /**
     * 测试token验证
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    @Test
    public void testVerifyToken() throws UnsupportedEncodingException {
        JWTHeader header = new JWTHeader();
        JWTPayload payload = new JWTPayload();
        payload.setIss("Yeager");
        payload.setIat(new Date());
        payload.setExp(DateTimeUtil.plusSecondBaseOnCurrentDate(10));
        payload.setNbf(DateTimeUtil.plusSecondBaseOnCurrentDate(2));
        payload.setJti(UUIDUtil.randomUUID());
        payload.setUserId(userId);

        String generateJWT = JWTUtil.generateJWT(header, payload, secret);
        System.out.println("JWT : " + generateJWT);

        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWTUtil.verifyToken(generateJWT, "fff");
        } catch (TokenExpiredException tokenExpired) {
            System.out.println("token过期");
            tokenExpired.printStackTrace();
        } catch (InvalidClaimException invalidClaim) {
            System.out.println("无效请求");
            invalidClaim.printStackTrace();
        } catch (SignatureVerificationException signatureVerification) {
            System.out.println("无效签名");
            signatureVerification.printStackTrace();
        } catch (JWTVerificationException jwtVerification) {
            System.out.println("JWT校验失败");
            jwtVerification.printStackTrace();
        }

        System.out.println("token : " + decodedJWT.getToken());
        System.out.println("header : " + decodedJWT.getHeader());
        System.out.println("payload : " + decodedJWT.getPayload());
        System.out.println("signature : " + decodedJWT.getSignature());
    }

}
