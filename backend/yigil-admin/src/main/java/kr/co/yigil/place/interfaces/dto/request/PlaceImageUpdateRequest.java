package kr.co.yigil.place.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceImageUpdateRequest {
    private Long placeId;
    private MultipartFile imageFile;

}
