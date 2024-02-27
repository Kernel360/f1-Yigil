package kr.co.yigil.travel.interfaces.dto.response;


import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MySpotsResponseDto {
    private List<SpotInfo> content;
    private int totalPages;

    @Getter
    @Builder
    @ToString
    public static class SpotInfo {

        private final Long spotId;
        private final String title;
        private final double rate;
        private final String imageUrl;
        private final String createdDate;
        private final Boolean isPrivate;
    }
}
