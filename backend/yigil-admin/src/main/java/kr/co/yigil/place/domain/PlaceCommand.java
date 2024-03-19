package kr.co.yigil.place.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class PlaceCommand {

    @Data
    public static class UpdateImageCommand {
        private Long placeId;
        private MultipartFile imageFile;
    }
}
