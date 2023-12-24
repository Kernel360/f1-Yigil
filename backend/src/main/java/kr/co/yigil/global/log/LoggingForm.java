package kr.co.yigil.global.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class LoggingForm {

    @Setter
    private String apiUrl;
    @Setter
    private String apiMethod;
    private Long queryCounts = 0L;
    private Long queryTime = 0L;

    public void queryCountUp() {
        queryCounts++;
    }

    public void addQueryTime(final Long queryTime) {
        this.queryTime += queryTime;
    }

}
