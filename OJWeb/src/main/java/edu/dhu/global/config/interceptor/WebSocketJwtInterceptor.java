package edu.dhu.global.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import edu.dhu.global.exception.Constants;
import edu.dhu.global.exception.ServiceException;
import edu.dhu.global.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Slf4j
public class WebSocketJwtInterceptor implements HandshakeInterceptor {

    @Resource
    private TokenUtil tokenUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        //获取参数
        String userId = serverHttpRequest.getServletRequest().getParameter("userId");
        String token = serverHttpRequest.getServletRequest().getParameter("token");
        if(StrUtil.isBlank((token)) || StrUtil.isBlank((userId))){
            return false;
        }
        try {
            tokenUtil.verifyToken(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        attributes.put("userId", userId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//        log.info("连接已经建立!");
    }
}
