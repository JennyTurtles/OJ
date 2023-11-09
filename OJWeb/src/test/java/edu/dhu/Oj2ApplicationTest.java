package edu.dhu;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
public class Oj2ApplicationTest {
    @Resource
    private RedisTemplate redisTemplate;
//    @Test
//    public void testBCryptPasswordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        System.out.println(bCryptPasswordEncoder.encode("felix"));
//    }
    // $2a$10$Ru/AVYKSxo6GMykr3KgSC.AadkHUPLKPN/k0zuFQrT7sZfMCXTTXy

//    @Test
//    public void testWS() {
//        MyWebSocketHandler.sendToUser("1", "hello");
//    }

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("test", "test");
        System.out.println(redisTemplate.opsForValue().get("test"));
    }
}