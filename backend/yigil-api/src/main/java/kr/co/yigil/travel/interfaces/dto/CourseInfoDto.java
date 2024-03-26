package kr.co.yigil.travel.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoDto {
    private Long id;
    private String title;
    private String content;
    private String mapStaticImageUrl;
    private double rate;
    private int spotCount;
    private LocalDateTime createDate;
    private Long ownerId;
    private String ownerProfileImageUrl;
    private String ownerNickname;
    private boolean liked;
    private boolean following;
}
