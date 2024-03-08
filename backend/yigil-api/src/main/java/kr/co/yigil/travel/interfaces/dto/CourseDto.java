package kr.co.yigil.travel.interfaces.dto;

import java.time.LocalDateTime;
import kr.co.yigil.auth.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDto {
    private Long id;
    private String title;
    private String mapStaticImageUrl;
    private String ownerProfileImageUrl;
    private String ownerNickname;
    private int spotCount;
    private double rate;
    private boolean liked;
    private LocalDateTime createDate;
}
