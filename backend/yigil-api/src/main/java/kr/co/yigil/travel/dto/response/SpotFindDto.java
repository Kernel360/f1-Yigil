package kr.co.yigil.travel.dto.response;

import kr.co.yigil.travel.Spot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotFindDto {
    private Long spotId;
    private String title;
    private String description;
    private String memberNickname;
    private String memberImageUrl;
    private Integer favorCount;
    private Integer commentCount;

    public static SpotFindDto from(Spot spot, Integer favorCount, Integer commentCount) {
        return new SpotFindDto(
                spot.getId(),
                spot.getTitle(),
                spot.getDescription(),
                spot.getMember().getNickname(),
                spot.getMember().getProfileImageUrl(),
                favorCount,
                commentCount
        );
    }
}
