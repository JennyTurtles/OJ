package edu.dhu;

import edu.dhu.ws.OJWSImpl2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class test {

    @Resource
    private OJWSImpl2 obj;

    @Test
    public void test1() {
        String a = obj.WS_Login("12","1213");
        System.out.println(a);
    }
}

