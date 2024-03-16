package kr.co.yigil.email;


public enum EmailEventType {

    ADMIN_SIGN_UP_ACCEPT("accept"),
    ADMIN_SIGN_UP_REJECT("reject"),
    REPORT_REPLY("report-reply.html");

    private final String templateName;

    EmailEventType(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String toString() {
        return templateName;
    }
}
