package kr.co.yigil.travel.interfaces.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotCreateResponse {
    private Long spotId;
    private String message;

}