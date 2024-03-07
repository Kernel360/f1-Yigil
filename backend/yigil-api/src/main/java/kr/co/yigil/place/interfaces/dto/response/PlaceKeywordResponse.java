package kr.co.yigil.place.interfaces.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceKeywordResponse {
    private List<String> keywords;
}
