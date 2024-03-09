package kr.co.yigil.admin.domain.admin;

import kr.co.yigil.admin.domain.Admin;

public interface AdminReader {

    boolean existsByEmailOrNickname(String email, String nickname);

    Admin getAdminByEmail(String email);

    Admin getAdmin(Long adminId);
}
