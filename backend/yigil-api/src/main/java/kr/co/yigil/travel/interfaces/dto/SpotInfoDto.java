package kr.co.yigil.travel.interfaces.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotInfoDto {
    private List<String> imageUrlList;

    private String ownerProfileImageUrl;

    private String ownerNickname;

    private String rate;

    private String createDate;
}
