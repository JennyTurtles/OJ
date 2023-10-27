package edu.dhu.global.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    public static String getBCryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
