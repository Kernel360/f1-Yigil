package kr.co.yigil.login.util;

public class LoginUtils {
    public static String extractToken(String authorizationHeader) {
        //
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // "Bearer " 다음부터가 실제 토큰
        }
        return null;
    }
}
