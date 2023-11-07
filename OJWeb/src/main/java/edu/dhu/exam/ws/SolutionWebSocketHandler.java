package edu.dhu.exam.ws;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.problem.model.PMWrongAndCorrectIds;
import edu.dhu.problem.service.SolutionServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Component
@Slf4j
public class SolutionWebSocketHandler extends TextWebSocketHandler {
    @Resource
    private SolutionServiceI solutionService;
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
        // 如果同一个用户重复登陆，关闭之前的连接
        if (sessionPools.containsKey(Integer.parseInt(userId))) {
            sessionPools.get(Integer.parseInt(userId)).close();
        }
        WebSocketSession put = sessionPools.put(Integer.parseInt(userId), session);
        if (put == null) {
            addOnlineCount();
        }
        session.sendMessage(new TextMessage("成功连接判题服务器！"));
        session.sendMessage(new TextMessage("【模拟接受消息】将在2秒后收到服务端发来的信息..."));
        Thread.sleep(2000);
        PMWrongAndCorrectIds res = (PMWrongAndCorrectIds) solutionService.getAllWrongAndRightCases(Integer.parseInt(userId), 26,581).getData();
        if (res != null) {
            String jsonString = JSON.toJSONString(res);
            sendToUser(userId, jsonString);
        }else{
            sendToUser(userId, "没有找到记录！");
        }
        log.info("用户：【{}】成功连接判题服务器！当前在线人数：【{}】",userId,onlineNum);

    }

    // 接受客户端消息,似乎OJ用不到。
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sendToUser(session.getAttributes().get("userId").toString(), "服务端已收到你的消息："+message.getPayload());
//        JSONObject jsonObject = JSON.parseObject(message.getPayload());
//        String toSid = jsonObject.getString("sid");
//        String msg = jsonObject.getString("message");
//        String userId = (String) session.getAttributes().get("userId");
//        sendToUser(toSid, String.format("用户：【%s】发来消息：【%s】",userId,msg));
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
