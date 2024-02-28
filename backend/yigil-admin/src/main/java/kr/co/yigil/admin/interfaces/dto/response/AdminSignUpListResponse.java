package kr.co.yigil.admin.interfaces.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminSignUpListResponse {
    private Long id;
    private String email;
    private String nickname;
    private String requestDatetime;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AdminSignUpListResponse(Long id, String email, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        requestDatetime = formatter.format(createdAt);
    }

    public void setRequestDateTime(LocalDateTime createdAt) {
        this.requestDatetime = formatter.format(createdAt);
    }
}


