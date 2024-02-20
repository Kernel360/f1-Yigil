package kr.co.yigil.travel.interfaces.dto;

import java.util.List;
import kr.co.yigil.travel.domain.spot.SpotInfo;
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

    public SpotInfoDto(SpotInfo.Main main) {
        this.imageUrlList = main.getImageUrlList();
        this.ownerProfileImageUrl = main.getOwnerProfileImageUrl();
        this.ownerNickname = main.getOwnerNickname();
        this.rate = main.getRate();
        this.createDate = main.getCreateDate();
    }
}
