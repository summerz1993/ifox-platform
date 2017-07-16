package com.ifox.platform.utility.jwt;

import java.util.Date;

/**
 * JWT Payload
 * https://tools.ietf.org/html/draft-ietf-oauth-json-web-token-32
 * https://jwt.io
 * https://github.com/auth0/java-jwt
 *
 * @author Yeager
 */
public class JWTPayload {

    /***************************** JWT标准字段 ****************************/

    /**
     * 签发者 issuer
     */
    private String iss;

    /**
     * 签发时间(时间戳) issued at
     */
    private Date iat;

    /**
     * 过期时间(时间戳) expire
     * 为防止Replay Attacks, 过期时间应该设置的比较短，比如exp = iat + 2(s)
     */
    private Date exp;

    /**
     * not before(时间戳)
     * 如果当前时间在nbf里的时间之前，则Token不被接受
     */
    private Date nbf;

    /**
     * JWT ID
     * 当前token的唯一标识, 使用UUID
     */
    private String jti;

    /**
     * 接收方 Audience
     */
    private String[] aud;

    /**
     * 面向的用户 Subject
     */
    private String sub;

    /***************************** 自定义字段 ****************************/

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 登陆名
     */
    private String loginName;

    /**
     * 登陆电话
     */
    private String loginMobile;

    /**
     * 角色列表
     */
    private String[] roleList;

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String[] getRoleList() {
        return roleList;
    }

    public void setRoleList(String[] roleList) {
        this.roleList = roleList;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginMobile() {
        return loginMobile;
    }

    public void setLoginMobile(String loginMobile) {
        this.loginMobile = loginMobile;
    }

    public String[] getAud() {
        return aud;
    }

    public void setAud(String[] aud) {
        this.aud = aud;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Date getIat() {
        return iat;
    }

    public void setIat(Date iat) {
        this.iat = iat;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public Date getNbf() {
        return nbf;
    }

    public void setNbf(Date nbf) {
        this.nbf = nbf;
    }
}
