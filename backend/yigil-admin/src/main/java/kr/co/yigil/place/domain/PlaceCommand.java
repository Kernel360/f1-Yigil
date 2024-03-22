package kr.co.yigil.place.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class PlaceCommand {

    @Getter
    public static class PlaceMapCommand {
        private final double startX;
        private final double startY;
        private final double endX;
        private final double endY;

        public PlaceMapCommand(double x1, double y1, double x2, double y2) {
            this.startX = x1;
            this.startY = y1;
            this.endX = x2;
            this.endY = y2;
        }
    }

    @Data
    public static class UpdateImageCommand {
        private final Long placeId;
        private final MultipartFile imageFile;
    }
}
