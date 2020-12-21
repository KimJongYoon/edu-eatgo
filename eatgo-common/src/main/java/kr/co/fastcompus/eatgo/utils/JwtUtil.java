package kr.co.fastcompus.eatgo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {


    private Key key;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 토큰 생성
     * @param userId
     * @param name
     * @return
     */
    public String createToken(Long userId, String name) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 클레임즈 얻기
     * @param token
     * @return
     */
    public Claims getClamils(String token) {
        return Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token) // 여기서 jws는 사인이 포함된 jwt를 의미함
            .getBody();

    }
}
