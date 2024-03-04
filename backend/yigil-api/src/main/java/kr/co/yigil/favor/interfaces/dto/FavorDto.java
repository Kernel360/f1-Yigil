package kr.co.yigil.favor.interfaces.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class FavorDto {
    @Getter
    @Builder
    @ToString
    public static class AddFavorResponse {
        private String message;
    }

    @Getter
    @Builder
    @ToString
    public static class DeleteFavorResponse {
        private String message;
    }
}
