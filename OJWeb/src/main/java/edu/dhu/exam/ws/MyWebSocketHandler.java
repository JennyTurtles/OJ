package edu.dhu.exam.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {
    private static AtomicInteger onlineNum = new AtomicInteger();

    /**
     * 存放每个客户端连接对象
     */
    private static ConcurrentHashMap<Integer, WebSocketSession> sessionPools = new ConcurrentHashMap<>();

    /**
     * 在线人数加一
     */
    public static void addOnlineCount() { onlineNum.incrementAndGet(); }

    /**
     * 在线人数减一
     */
    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = session.getAttributes().get("userId").toString();
        WebSocketSession put = sessionPools.put(Integer.parseInt(userId), session);
        if (put == null) {
            addOnlineCount();
        }
        session.sendMessage(new TextMessage("成功连接判题服务器！"));
    }

    // 接受客户端消息,似乎OJ用不到。
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        String text = message.getPayload();
        session.sendMessage(new TextMessage(String.format("收到用户：【%s】发来的【%s】",userId,text)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = session.getAttributes().get("userId").toString();
        sessionPools.remove(Integer.parseInt(userId));
        subOnlineCount();
    }

    public static void sendToUser(String userId, String message) {
        WebSocketSession socketSession = sessionPools.get(Integer.parseInt(userId));
        if (socketSession == null) {
            return;
        }
        try {
            socketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("send to user:{}, error! data:{}", userId, message);
        }
    }
}
