package edu.dhu.global.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    public static String getBCryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static boolean checkPassword(String password, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(password, encodedPassword);
    }
}
