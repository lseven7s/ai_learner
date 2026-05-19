package com.ai.learning.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("原始密码: " + rawPassword);
        System.out.println("BCrypt哈希: " + encodedPassword);
        System.out.println();
        System.out.println("再生成一个用于验证:");
        String encodedPassword2 = encoder.encode(rawPassword);
        System.out.println("第二个哈希: " + encodedPassword2);
        System.out.println();
        System.out.println("验证匹配: " + encoder.matches(rawPassword, encodedPassword));
        System.out.println("验证匹配2: " + encoder.matches(rawPassword, encodedPassword2));
    }
}
