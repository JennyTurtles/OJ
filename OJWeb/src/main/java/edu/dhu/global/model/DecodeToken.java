package edu.dhu.global.model;

import com.auth0.jwt.JWT;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class DecodeToken {
    private String userId;
    private String role;

    public DecodeToken(HttpServletRequest request) {
        userId = JWT.decode(request.getHeader("Authorization")).getAudience().get(0);
        role = JWT.decode(request.getHeader("Authorization")).getClaims().get("role").asString();
    }
}
