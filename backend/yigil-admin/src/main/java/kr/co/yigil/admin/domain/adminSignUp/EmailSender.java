package kr.co.yigil.admin.domain.adminSignUp;

import kr.co.yigil.admin.domain.AdminSignUp;
import kr.co.yigil.report.report.domain.Report;

public interface EmailSender {

    void sendAcceptEmail(AdminSignUp signUp, String password);

    void sendRejectEmail(AdminSignUp signUp);

    void replyReport(String content, Report report);
}
