package kr.co.yigil.travel.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  SpotUpdateRequest {

    private String title;
    private String description;
    private List<MultipartFile> files;
    private double rate;

}
