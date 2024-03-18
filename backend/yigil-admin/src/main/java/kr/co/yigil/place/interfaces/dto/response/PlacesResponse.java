package kr.co.yigil.place.interfaces.dto.response;

import java.awt.Point;
import kr.co.yigil.member.interfaces.dto.response.MemberInfoDto;
import kr.co.yigil.place.interfaces.dto.PlaceInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlacesResponse {
    private Page<PlaceInfoDto> members;

}
