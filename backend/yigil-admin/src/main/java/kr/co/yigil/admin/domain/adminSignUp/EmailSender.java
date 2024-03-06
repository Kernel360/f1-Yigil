package kr.co.yigil.admin.domain.adminSignUp;

import kr.co.yigil.admin.domain.AdminSignUp;

public interface EmailSender {

    void sendAcceptEmail(AdminSignUp signUp, String password);

    void sendRejectEmail(AdminSignUp signUp);
}
