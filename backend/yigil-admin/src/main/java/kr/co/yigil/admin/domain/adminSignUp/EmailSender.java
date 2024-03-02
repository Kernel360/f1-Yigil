package kr.co.yigil.admin.domain.adminSignUp;

public interface EmailSender {

    void sendAcceptEmail(AdminSignUp signUp, String password);

    void sendRejectEmail(AdminSignUp signUp);
}
