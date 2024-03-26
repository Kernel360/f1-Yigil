package kr.co.yigil.travel.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotInfoDto {
    private Long id;

    private List<String> imageUrlList;

    private String description;

    private Long ownerId;

    private String ownerProfileImageUrl;

    private String ownerNickname;

    private double rate;

    private LocalDateTime createDate;

    private boolean liked;

    private boolean following;
}
