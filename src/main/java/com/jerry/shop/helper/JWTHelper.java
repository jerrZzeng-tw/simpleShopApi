package com.jerry.shop.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jerry.shop.ConstantKey;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class JWTHelper {

    private final JWTVerifier verifier;

    private static final String CLAIMS_KEY_USER_ROLES = "userRoles";
    private static final JWTHelper INSTANCE = new JWTHelper();
    private final Algorithm algorithm = Algorithm.HMAC256(ConstantKey.JWT_ALGKEY);

    private JWTHelper() {
        verifier = JWT.require(algorithm).withIssuer(ConstantKey.JWT_ISSUER).build(); // Reusable verifier instance
    }

    public static String getUserName(String token) {
        return getJWTdata(token).getId();
    }


    public static DecodedJWT getJWTdata(String token) {
        return INSTANCE.verifier.verify(token);
    }

    public static String getToken(String userName, List<String> roles) {
        return JWT.create()
                .withIssuer(ConstantKey.JWT_ISSUER)
                .withExpiresAt(java.util.Date.from(
                        LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()))
                .withJWTId(userName)
                .withClaim(CLAIMS_KEY_USER_ROLES, roles)
                .sign(INSTANCE.algorithm);
    }

    public static List<SimpleGrantedAuthority> getUserAuthorities(String token) {
        List<String> userRoles = getJWTdata(token).getClaim(CLAIMS_KEY_USER_ROLES).asList(String.class);
        return userRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }
}
