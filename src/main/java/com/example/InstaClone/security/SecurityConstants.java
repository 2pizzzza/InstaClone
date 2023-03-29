package com.example.InstaClone.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/auth/**";

    public static final String SECRET = "SecretKeyGenJWT";
    public static final String TOKENP_PREFIX ="Bearer ";
    public static final String HEADER_STRING = "Autorization";
    public static final String CONTENT_TYPE ="application/json";
    public static final long EXPIRATION_TIME = 600_000; //10min
}
