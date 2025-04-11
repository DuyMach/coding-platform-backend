package com.codingplatform.coding_platform_backend.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecurityConstant {
    public static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24;
    public static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
}
