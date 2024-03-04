package kr.co.yigil.admin.domain.admin;

public interface AdminReader {

    boolean existsByEmailOrNickname(String email, String nickname);

    Admin getAdminByEmail(String email);
}
