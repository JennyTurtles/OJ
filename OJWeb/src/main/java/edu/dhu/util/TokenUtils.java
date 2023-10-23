package edu.dhu.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtils {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public String genToken(String userId, String role) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // 密钥保存在配置文件中
        if(role == null)
            role = "student";
        return JWT.create().withAudience(userId).withClaim("role", role) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }

}
