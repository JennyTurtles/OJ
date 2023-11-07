package edu.dhu;
import edu.dhu.exam.ws.MyWebSocketHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
public class Oj2ApplicationTest {

    @Resource
    MyWebSocketHandler myWebSocketHandler;
    @Test
    public void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("felix"));
    }
    // $2a$10$Ru/AVYKSxo6GMykr3KgSC.AadkHUPLKPN/k0zuFQrT7sZfMCXTTXy

//    @Test
//    public void testWS() {
//        MyWebSocketHandler.sendToUser("1", "hello");
//    }
}