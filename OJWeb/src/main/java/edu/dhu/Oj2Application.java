package edu.dhu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ImportResource("classpath:spring-hibernate.xml")
@SpringBootApplication
public class Oj2Application {

    public static void main(String[] args) {
        SpringApplication.run(Oj2Application.class, args);
    }

}
