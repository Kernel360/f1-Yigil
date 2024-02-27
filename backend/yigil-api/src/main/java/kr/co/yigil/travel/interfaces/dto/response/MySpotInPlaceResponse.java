package kr.co.yigil.travel.interfaces.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MySpotInPlaceResponse {
    private boolean exists;
    private String rate;
    private List<String> imageUrls;
    private String createDate;
    private String description;
}
