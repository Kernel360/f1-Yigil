package kr.co.yigil.travel.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoDto {
    private String mapStaticImageFileUrl;
    private String title;
    private String rate;
    private String spotCount;
    private String createDate;
    private String ownerProfileImageUrl;
    private String ownerNickname;
}
