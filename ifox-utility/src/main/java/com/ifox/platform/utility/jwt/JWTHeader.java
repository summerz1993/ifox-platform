package com.ifox.platform.utility.jwt;

/**
 * JWT Header
 * @author Yeager
 */
public class JWTHeader {

    /**
     * 加密算法
     */
    private String alg = "HS256";

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getTyp() {
        return "JWT";
    }

}
