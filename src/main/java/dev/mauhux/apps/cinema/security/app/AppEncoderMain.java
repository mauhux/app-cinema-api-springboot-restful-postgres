package dev.mauhux.apps.cinema.security.app;

import java.util.Base64;

public class AppEncoderMain {

    public static void main(String[] args) {
        // System.out.println(new BCryptPasswordEncoder().encode("123"));

        String encodedString = Base64.getEncoder().encodeToString("Mauhux=On+Mode_Full-Stack-Developer@2025%".getBytes());
        System.out.println(encodedString);

        byte[] encoded = Base64.getDecoder().decode("TWV1aHVuPXdvT0RlVnBvZl9GdWxsLVN0YWNrLURldmVsb3BlckAyMDI1JQ==");
        System.out.println(new String(encoded));
    }
}
